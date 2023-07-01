import SwiftUI

struct TemplateView: View {
    @State private var templates: [Template] = []
    @State private var categories: [Category] = []
    @State private var isPresentingForm = false
    @State private var templateToDelete: Template?
    private let refreshTemplates = NotificationCenter.default.publisher(for: Notification.Name("refreshTemplates"))

    var body: some View {
        VStack {
            List {
                ForEach(templates) { template in
                    VStack(alignment: .leading) {
                        HStack {
                            Text("Category: \(categoryName(for: template.categoryId))")
                                .font(.headline)
                            Spacer()
                            Button(action: {
                                templateToDelete = template
                            }) {
                                Image(systemName: "trash")
                                    .foregroundColor(.red)
                            }
                            .alert(item: $templateToDelete) { template in
                                Alert(
                                    title: Text("Confirmation"),
                                    message: Text("Are you sure you want to delete this template?"),
                                    primaryButton: .cancel(Text("Cancel")),
                                    secondaryButton: .destructive(Text("Delete"), action: {
                                        deleteTemplate(template)
                                    })
                                )
                            }
                        }
                        Text("Created at: \(convertToLocalTime(template.createdAt))")
                        Text("Updated at: \(convertToLocalTime(template.updatedAt))")
                        ForEach(template.templateRequirements, id: \.requirementName) { requirement in
                            Text("\(requirement.requirementName): \(requirement.requirementValue ?? "No value")")
                        }
                        Button(action: {
                            print("Edit button tapped for template \(template.id)")
                        }) {
                            Text("Edit")
                                .frame(maxWidth: .infinity, alignment: .center)
                                .padding()
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .cornerRadius(10)
                        }
                    }
                    .padding()
                    .background(Color.white)
                    .cornerRadius(10)
                    .shadow(radius: 10)
                }
                
                Button(action: {
                    isPresentingForm = true
                }) {
                    Text("+")
                        .font(.largeTitle)
                        .frame(maxWidth: .infinity, alignment: .center)
                        .padding()
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                .shadow(radius: 10)
            }
            .onAppear {
                fetchTemplates()
                fetchCategories()
            }
            .sheet(isPresented: $isPresentingForm) {
                TemplateForm(isPresented: $isPresentingForm)
            }
            .onReceive(refreshTemplates) { _ in
                fetchTemplates()
            }
        }
    }

    private func fetchTemplates() {
        guard let userId = UserDefaults.standard.string(forKey: "id") else { return }
        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
        guard let url = URL(string: "http://localhost:8080/api/v1/user/\(userId)/templates") else { return }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if let data = data {
                do {
                    let decoder = JSONDecoder()
                    let templatesData = try decoder.decode([Template].self, from: data)
                    DispatchQueue.main.async {
                        self.templates = templatesData
                    }
                } catch {
                    print("Error decoding JSON: \(error)")
                }
            } else if let error = error {
                print("HTTP Request Failed \(error)")
            }
        }

        task.resume()
    }

    private func convertToLocalTime(_ utcTime: String) -> String {
        let dateFormatter = ISO8601DateFormatter()
        guard let date = dateFormatter.date(from: utcTime) else { return utcTime }

        dateFormatter.timeZone = TimeZone.current
        return dateFormatter.string(from: date)
    }

    private func fetchCategories() {
        guard let url = URL(string: "http://localhost:8080/api/v1/templates/categories") else { return }
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if let data = data {
                do {
                    let decoder = JSONDecoder()
                    let categoriesData = try decoder.decode([Category].self, from: data)
                    DispatchQueue.main.async {
                        self.categories = categoriesData
                    }
                } catch {
                    print("Error decoding JSON: \(error)")
                }
            } else if let error = error {
                print("HTTP Request Failed \(error)")
            }
        }

        task.resume()
    }

    private func categoryName(for id: Int) -> String {
        return categories.first(where: { $0.id == id })?.categoryName ?? "Unknown"
    }
    
    private func deleteTemplate(_ template: Template) {
        guard let userId = UserDefaults.standard.string(forKey: "id") else { return }
        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
        let templateId = template.id
        guard let url = URL(string: "http://localhost:8080/api/v1/user/\(userId)/templates/\(templateId)") else { return }
        
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        
        let task = URLSession.shared.dataTask(with: request) { _, response, error in
            if let error = error {
                print("HTTP Request Failed \(error)")
            } else {
                DispatchQueue.main.async {
                    fetchTemplates()
                }
            }
        }
        
        task.resume()
    }

}
