//
//  ContentView.swift
//  Client Connect App
//
//  Created by Volodymyr Motrechko on 14.06.2023.
//

import SwiftUI

struct ContentView: View {
    @State private var isLoggedIn = false

    var body: some View {
        if isLoggedIn {
            MainView()
        } else {
            LoginView(isLoggedIn: $isLoggedIn)
        }
    }
}


