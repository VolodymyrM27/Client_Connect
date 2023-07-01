import SwiftUI
import Combine

struct EditableRequirement: Codable, Hashable, Identifiable {
    let id: Int
    let requirementName: String
    var requirementValue: String?
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case requirementName = "requirement_name"
    }
}

struct TemplateFormModel: Codable {
    let userId: Int
    let categoryId: Int
    let isPublic: Bool
    let templateRequirements: [TemplateFormRequirement]

    enum CodingKeys: String, CodingKey {
        case userId = "user_id"
        case categoryId = "category_id"
        case isPublic = "is_public"
        case templateRequirements = "template_requirements"
    }
}

struct TemplateFormRequirement: Codable {
    let requirementId: Int
    let requirementName: String
    let requirementValue: String

    enum CodingKeys: String, CodingKey {
        case requirementId = "requirement_id"
        case requirementName = "requirement_name"
        case requirementValue = "requirement_value"
    }
}




class FormState: ObservableObject {
    @Published var isPublic = false
    @Published var categoryId: Int = 1
    @Published var requirements: [EditableRequirement] = []
    @Published var categories: [Category] = []
}

struct TemplateForm: View {
    @Environment(\.presentationMode) var presentationMode
    @ObservedObject var formState = FormState()
    @State private var selectedCategoryId = 1
    @State private var newRequirementValue: String = ""
    @Binding var isPresented: Bool
    
    var body: some View {
        NavigationView {
            VStack {
                Form {
                    Section(header: Text("Category")) {
                        Picker("Select Category", selection: $selectedCategoryId) {
                            ForEach(formState.categories, id: \.id) { category in
                                Text(category.categoryName).tag(category.id)
                            }
                        }
                        .onChange(of: selectedCategoryId) { newCategoryId in
                            fetchRequirements(for: newCategoryId)
                        }
                    }
                    
                    Section(header: Text("Requirements")) {
                        ForEach(Array(formState.requirements.enumerated()), id: \.element.id) { index, requirement in
                            TextField(requirement.requirementName, text: Binding(
                                get: { formState.requirements[index].requirementValue ?? "" },
                                set: { formState.requirements[index].requirementValue = $0 }
                            ))
                        }
                    }
                    
                    
                    Section {
                        Toggle(isOn: $formState.isPublic) {
                            Text("Is Public")
                        }
                    }
                    
                    Button(action: sendForm) {
                        Text("Send")
                    }
                }
            }
            .navigationBarTitle("New Template", displayMode: .inline)
            .navigationBarItems(trailing: Button(action: {
                presentationMode.wrappedValue.dismiss()
            }) {
                Text("Close")
            })
            .onAppear {
                fetchCategories()
            }
        }
    }
    
    func fetchRequirements(for categoryId: Int) {
        // Use your actual API endpoint here
        
        guard let url = URL(string: "http://localhost:8080/api/v1/templates/categories/\(categoryId)/requirement") else { return }
        print(url)
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let httpResponse = response as? HTTPURLResponse {
                print("HTTP Response Code: \(httpResponse.statusCode)")
            }
            
            if let data = data {
                do {
                    // Convert data to a string and print it
                    let dataString = String(data: data, encoding: .utf8)
                    print("Received data:\n\(dataString ?? "")")
                    
                    let fetchedRequirements = try JSONDecoder().decode([EditableRequirement].self, from: data)
                    DispatchQueue.main.async {
                        self.formState.requirements = fetchedRequirements
                    }
                } catch {
                    print("Error decoding requirements: \(error)")
                }
            } else {
                print("Error fetching requirements: \(error?.localizedDescription ?? "Unknown error")")
            }
        }
        
        task.resume()
    }
    
    
    func fetchCategories() {
        // Use your actual API endpoint here
        guard let url = URL(string: "http://localhost:8080/api/v1/templates/categories") else { return }
        
        let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
            if let data = data {
                do {
                    let fetchedCategories = try JSONDecoder().decode([Category].self, from: data)
                    DispatchQueue.main.async {
                        self.formState.categories = fetchedCategories
                    }
                } catch {
                    print("Error decoding categories: \(error)")
                }
            } else {
                print("Error fetching categories: \(error?.localizedDescription ?? "Unknown error")")
            }
        }
        
        task.resume()
    }
    
 
    func sendForm() {
        guard let userIdString = UserDefaults.standard.string(forKey: "id"), let userId = Int(userIdString) else { return }
        let filledRequirements = formState.requirements.filter { $0.requirementValue != nil }
        
        let templateFormRequirements = filledRequirements.map { TemplateFormRequirement(requirementId: $0.id, requirementName: $0.requirementName, requirementValue: $0.requirementValue ?? "") }

        let form = TemplateFormModel(userId: userId, categoryId: formState.categoryId, isPublic: formState.isPublic, templateRequirements: templateFormRequirements)
        
        guard let url = URL(string: "http://localhost:8080/api/v1/templates") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        
        do {
            let encoder = JSONEncoder()
            let data = try encoder.encode(form)
            request.httpBody = data
            
            let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
                if let httpResponse = response as? HTTPURLResponse {
                    print("HTTP Response Code: \(httpResponse.statusCode)")
                }
                
                if let error = error {
                    print("Error sending form: \(error)")
                } else {
                    DispatchQueue.main.async {
                        self.presentationMode.wrappedValue.dismiss()
                        // Notify to update the template list
                        NotificationCenter.default.post(name: Notification.Name("refreshTemplates"), object: nil)
                    }
                }
            }
            
            task.resume()
        } catch {
            print("Failed to encode form: \(error)")
        }
    }



        
        
    
}
