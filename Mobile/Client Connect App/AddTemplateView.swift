//import SwiftUI
//
//struct AddTemplateView: View {
//    @State private var categories: [Category] = []
//    @State private var selectedCategoryId: Int?
//    @State private var isPublic: Bool = false
//    @State private var requirements: [FetchedRequirement] = []
//    @State private var requirementsValues: [String] = []
//    @Binding var isPresented: Bool
//    
//    var body: some View {
//        NavigationView {
//            Form {
//                
//                Picker("Category", selection: $selectedCategoryId) {
//                    ForEach(categories, id: \.id) { category in
//                        Text(category.categoryName).tag(category.id)
//                    }
//                }
//                .onChange(of: selectedCategoryId) { value in
//                    fetchRequirements(for: value)
//                }
//                
//                Toggle(isOn: $isPublic) {
//                    Text("Is Public")
//                }
//                
//                ForEach(requirements.indices, id: \.self) { index in
//                    TextField(requirements[index].requirementName, text: $requirementsValues[index])
//                }
//            }
//            .navigationTitle("Add Template")
//            .toolbar {
//                ToolbarItem(placement: .navigationBarLeading) {
//                    Button("Cancel") {
//                        isPresented = false
//                    }
//                }
//                ToolbarItem(placement: .navigationBarTrailing) {
//                    Button("Save") {
//                        saveTemplate()
//                    }
//                }
//            }
//        }
//        .onAppear(perform: fetchCategories)
//    }
//    
//    
//    private func fetchRequirements(for categoryId: Int?) {
//        guard let categoryId = categoryId else { return }
//        
//        guard let url = URL(string: "http://localhost:8080/api/v1/templates/categories/\(categoryId)/requirement") else { return }
//        
//        var request = URLRequest(url: url)
//        request.httpMethod = "GET"
//        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
//        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
//        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
//        
//        let task = URLSession.shared.dataTask(with: request) { data, response, error in
//            if let data = data {
//                print(String(data: data, encoding: .utf8) ?? "No data")
//                do {
//                    let decoder = JSONDecoder()
//                           let requirementsData = try decoder.decode([FetchedRequirement].self, from: data)
//                    DispatchQueue.main.async {
//                        self.requirements = requirementsData
//                        self.requirementsValues = Array(repeating: "", count: requirementsData.count)
//                    }
//                } catch {
//                    print("Error decoding JSON: \(error)")
//                }
//            } else if let error = error {
//                print("HTTP Request Failed \(error)")
//            }
//        }
//        
//        task.resume()
//    }
//    
//    private func saveTemplate() {
//        guard let categoryId = selectedCategoryId else { return }
//        let userId = UserDefaults.standard.integer(forKey: "id");
//
//        //let templateRequirements = zip(requirements, requirementsValues).map { requirement, value -> TemplateRequirement in
//          //      TemplateRequirement(requirementId: requirement.id, requirementName: requirement.requirementName, requirementValue: value)
//        //    }
////
////        let newTemplate = NewTemplate(
////            userId: userId,
////            categoryId: categoryId,
////            isPublic: isPublic,
//        //    templateRequirements: templateRequirements
//       // )
//        
//        guard let url = URL(string: "http://localhost:8080/api/v1/templates") else { return }
//        var request = URLRequest(url: url)
//        request.httpMethod = "POST"
//        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
//        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
//        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
////        guard let httpBody = try? JSONEncoder().encode(newTemplate) else {
////            print("Failed to encode the new template")
////            return
////        }
//    //    request.httpBody = httpBody
//        
//        let task = URLSession.shared.dataTask(with: request) { data, response, error in
//            if let error = error {
//                print("HTTP Request Failed \(error)")
//            } else if let response = response as? HTTPURLResponse, response.statusCode == 201 {
//                DispatchQueue.main.async {
//                    self.isPresented = false
//                }
//            }
//        }
//        
//        task.resume()
//    }
//    
//    private func fetchCategories() {
//        guard let url = URL(string: "http://localhost:8080/api/v1/templates/categories") else { return }
//        
//        var request = URLRequest(url: url)
//        request.httpMethod = "GET"
//        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
//        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
//        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
//        
//        let task = URLSession.shared.dataTask(with: request) { data, response, error in
//            if let data = data {
//                do {
//                    let decoder = JSONDecoder()
//                    let categoriesData = try decoder.decode([Category].self, from: data)
//                    DispatchQueue.main.async {
//                        self.categories = categoriesData
//                        if let firstCategoryId = categoriesData.first?.id {
//                            selectedCategoryId = firstCategoryId
//                        }
//                    }
//                } catch {
//                    print("Error decoding JSON: \(error)")
//                }
//            } else if let error = error {
//                print("HTTP Request Failed \(error)")
//            }
//        }
//        
//        task.resume()
//    }
//}
