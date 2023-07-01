//
//  LoginView.swift
//  Client Connect App
//
//  Created by Volodymyr Motrechko on 14.06.2023.
//

import SwiftUI

struct LoginView: View {
    @State private var email = ""
    @State private var password = ""
    @Binding var isLoggedIn: Bool
    @State private var showAlert = false
    @State private var alertMessage = ""

    var body: some View {
        VStack {
            Text("Client Connect")
                .font(.largeTitle)
                .fontWeight(.semibold)
                .padding(.bottom, 20)
            
            VStack(alignment: .leading) {
                Text("Email")
                    .font(.headline)
                TextField("Enter your email", text: $email)
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                    .padding()
                    .background(RoundedRectangle(cornerRadius: 10).strokeBorder(Color.blue, lineWidth: 1.0))
            }
            .padding(.bottom, 10)
            
            VStack(alignment: .leading) {
                Text("Password")
                    .font(.headline)
                SecureField("Enter your password", text: $password)
                    .padding()
                    .background(RoundedRectangle(cornerRadius: 10).strokeBorder(Color.blue, lineWidth: 1.0))
            }
            .padding(.bottom, 20)
            
            Button(action: {
                loginButtonTapped()
            }) {
                Text("Login")
                    .font(.headline)
                    .foregroundColor(.white)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .cornerRadius(10)
            }
            .padding(.bottom, 10)

            Button(action: {
                // navigate to registration view
            }) {
                Text("Don't have an account? Register")
                    .font(.subheadline)
                    .foregroundColor(.blue)
            }
            
            Spacer()
        }
        .padding()
        .alert(isPresented: $showAlert) {
            Alert(title: Text("Error"), message: Text(alertMessage), dismissButton: .default(Text("OK")))
        }
    }
    
    private func loginButtonTapped() {
        guard
            !email.isEmpty,
            !password.isEmpty
        else {
            // Показать сообщение об ошибке, что поля не должны быть пустыми
            alertMessage = "Email and password should not be empty"
            showAlert = true
            return
        }
        
        let loginDetails = ["email": email, "password": password]
        print(loginDetails)

        do {
            let jsonData = try JSONSerialization.data(withJSONObject: loginDetails, options: .prettyPrinted)
            sendLoginDetailsToServer(data: jsonData)
        } catch {
            // Обработать ошибку преобразования в JSON
            alertMessage = "Error converting login details to JSON"
            showAlert = true
        }
    }
    
    private func sendLoginDetailsToServer(data: Data) {
        let url = URL(string: "http://localhost:8080/api/v1/auth/authenticate")!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = data
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")

        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            if let error = error {
                print("Error: \(error)")
                DispatchQueue.main.async {
                    alertMessage = "Error: \(error.localizedDescription)"
                    showAlert = true
                }
            } else if let data = data {
                do {
                    if let jsonResult = try JSONSerialization.jsonObject(with: data, options: []) as? [String:Any] {
                        if let message = jsonResult["message"] as? String {
                            DispatchQueue.main.async {
                                alertMessage = message
                                showAlert = true
                            }
                        } else {
                            DispatchQueue.main.async {
                                let defaults = UserDefaults.standard
                                defaults.setValue(jsonResult["token"], forKey: "token")
                                defaults.setValue(jsonResult["role"], forKey: "role")
                                defaults.setValue(jsonResult["id"], forKey: "id")
                                isLoggedIn = true
                            }
                        }
                    }
                } catch {
                    print("Error: \(error)")
                    DispatchQueue.main.async {
                        alertMessage = "Error: \(error.localizedDescription)"
                        showAlert = true
                    }
                }
            }
        }
        task.resume()
    }
}


