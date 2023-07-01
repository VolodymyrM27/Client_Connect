//
//  MainView.swift
//  Client Connect App
//
//  Created by Volodymyr Motrechko on 14.06.2023.
//

import SwiftUI

struct MainView: View {
    @State private var selectedTab = 0
    
    var body: some View {
        TabView(selection: $selectedTab) {
            TemplateView()
                .tabItem {
                    Image(systemName: "doc.text")
                    Text("Templates")
                }.tag(0)
            
            Text("Account")
                .tabItem {
                    Image(systemName: "person.circle")
                    Text("Account")
                }.tag(1)
            
            HistoryView()
                .tabItem {
                    Image(systemName: "clock")
                    Text("History")
                }.tag(2)
        }
    }
}


struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
