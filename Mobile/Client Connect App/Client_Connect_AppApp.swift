//
//  Client_Connect_AppApp.swift
//  Client Connect App
//
//  Created by Volodymyr Motrechko on 14.06.2023.
//

import SwiftUI

@main
struct Client_Connect_AppApp: App {
    @State private var isLoggedIn = false

    var body: some Scene {
        WindowGroup {
            if isLoggedIn {
                MainView()
            } else {
                LoginView(isLoggedIn: $isLoggedIn)
            }
        }
    }
}

