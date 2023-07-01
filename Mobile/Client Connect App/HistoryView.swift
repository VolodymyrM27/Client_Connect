import SwiftUI

struct HistoryView: View {
    @State private var historyItems: [HistoryItem] = []
    @State private var business: Business?
    @State private var template: Template?
    @State private var showingDetail = false
    @State private var isLoadingBusiness = false
    @State private var isLoadingTemplate = false
    @State private var isSheetShowing = false

    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack {
                    ForEach(historyItems, id: \.self) { item in
                        VStack(alignment: .leading) {
                            Text(item.businessBusinessName)
                            Text(convertToLocale(item.usedAt))
                            Button(action: {
                                fetchBusiness(businessId: item.businessId)
                            }) {
                                Text("Show business")
                                    .frame(minWidth: 0, maxWidth: .infinity)
                                    .padding()
                                    .foregroundColor(.white)
                                    .background(Color.green)
                                    .cornerRadius(40)
                            }
                            
                            Button(action: {
                                fetchTemplate(templateId: item.templateId)
                            }) {
                                Text("Show Template")
                                    .frame(minWidth: 0, maxWidth: .infinity)
                                    .padding()
                                    .foregroundColor(.white)
                                    .background(Color.green)
                                    .cornerRadius(40)
                            }
                        }
                        .padding()
                        .background(Color.white)
                        .cornerRadius(10)
                        .shadow(radius: 5)
                        .padding(.bottom)
                    }
                }
                .padding()
            }
            .onAppear(perform: loadData)
            .sheet(isPresented: $isSheetShowing) {
                VStack(alignment: .leading) {
                    if isLoadingBusiness || isLoadingTemplate{
                        ProgressView()
                    }else {
                        Group {
                            
                            if let business = business {
                                Text("Business Details")
                                    .font(.title)
                                    .fontWeight(.bold)
                                    .padding(.bottom)
                                
                                Text("Name: \(business.businessName)")
                                    .font(.headline)
                                Text("Category: \(business.category.categoryName)")
                                    .font(.headline)
                                Text("Address: \(business.address)")
                                    .font(.headline)
                                Text("Created at: \(business.createdAt)")
                                    .font(.headline)
                            } else if let template = template {
                                Text("Template Details")
                                    .font(.title)
                                    .fontWeight(.bold)
                                    .padding(.bottom)
                                
                                Text("Created at: \(template.createdAt)")
                                    .font(.headline)
                                Text("Updated at: \(template.updatedAt)")
                                    .font(.headline)
                                
                                ForEach(template.templateRequirements, id: \.self) { req in
                                    Text("\(req.requirementName): \(req.requirementValue)")
                                        .font(.headline)
                                }
                            }
                        }
                        .padding()
                        Spacer()
                    }
                }
                .background(Color.white)
                .cornerRadius(10)
                .shadow(radius: 5)
                .padding()
            
        }
    }
}

func loadData() {
    guard let userId = UserDefaults.standard.string(forKey: "id") else { return }
    guard let token = UserDefaults.standard.string(forKey: "token") else { return }
    guard let url = URL(string: "http://localhost:8080/api/v1/user/\(userId)/history") else { return }
    
    var request = URLRequest(url: url)
    request.httpMethod = "GET"
    request.addValue("application/json", forHTTPHeaderField: "Content-Type")
    request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
    
    let task = URLSession.shared.dataTask(with: request) { data, response, error in
        if let data = data {
            do {
                if let json = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
                    print(json)
                }
                let decoder = JSONDecoder()
                let historyData = try decoder.decode([HistoryItem].self, from: data)
                DispatchQueue.main.async {
                    self.historyItems = historyData
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


    func fetchBusiness(businessId: Int) {
        isLoadingBusiness = true
        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
        guard let url = URL(string: "http://localhost:8080/api/v1/business/\(businessId)") else { return }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let data = data {
                do {
                    let jsonObject = try JSONSerialization.jsonObject(with: data, options: [])
                    print("Decoded JSON business: \(jsonObject)")
                    let decoder = JSONDecoder()
                    let businessData = try decoder.decode(Business.self, from: data)
                    DispatchQueue.main.async {
                        self.isLoadingBusiness = false
                        self.business = businessData
                        self.template = nil
                        self.isSheetShowing = true
                    }
                } catch {
                    print("Error decoding JSON business: \(error)")
                    print("Failed to decode data: \(String(data: data, encoding: .utf8) ?? "Could not convert data to string")")
                }
            } else if let error = error {
                print("HTTP Request Failed \(error)")
            }
        }.resume()
    }
    

    func fetchTemplate(templateId: Int) {
        isLoadingTemplate = true
        guard let token = UserDefaults.standard.string(forKey: "token") else { return }
        guard let url = URL(string: "http://localhost:8080/api/v1/templates/\(templateId)") else { return }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("Bearer \(token)", forHTTPHeaderField: "Authorization")

        URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let data = data {
                do {
                    let jsonObject = try JSONSerialization.jsonObject(with: data, options: [])
                    print("Decoded JSON template: \(jsonObject)")
                    let decoder = JSONDecoder()
                    let templateData = try decoder.decode(Template.self, from: data)
                    DispatchQueue.main.async {
                        self.isLoadingTemplate = false
                        self.template = templateData
                        self.business = nil
                        self.isSheetShowing = true
                    }
                } catch {
                    print("Error decoding JSON template: \(error)")
                    print("Failed to decode data: \(String(data: data, encoding: .utf8) ?? "Could not convert data to string")")
                }
            } else if let error = error {
                print("HTTP Request Failed \(error)")
            }
        }.resume()
    }


func convertToLocale(_ dateStr: String) -> String {
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    dateFormatter.timeZone = TimeZone(secondsFromGMT: 0)
    guard let date = dateFormatter.date(from: dateStr) else { return "" }
    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
    dateFormatter.timeZone = TimeZone.current
    let localDateStr = dateFormatter.string(from: date)
    return localDateStr
}
}
