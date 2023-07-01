//
//  Models.swift
//  Client Connect App
//
//  Created by Volodymyr Motrechko on 14.06.2023.
//

import Foundation

//struct Template: Codable, Identifiable {
//    let id: Int
//    let userId: Int
//    let categoryId: Int
//    let createdAt: String
//    let updatedAt: String
//    let isPublic: Bool
//    let reviewIds: [Int]
//    let templateRequirements: [Requirement]
//    let userTemplateHistoryIds: [Int]
//    let status: String
//
//    enum CodingKeys: String, CodingKey {
//        case id
//        case userId = "user_id"
//        case categoryId = "category_id"
//        case createdAt = "created_at"
//        case updatedAt = "updated_at"
//        case isPublic = "is_public"
//        case reviewIds = "review_ids"
//        case templateRequirements = "template_requirements"
//        case userTemplateHistoryIds = "user_template_history_ids"
//        case status
//    }
//}

struct TemplateRequirement: Codable {
    let requirementId: Int
    let requirementName: String
    let requirementValue: String
}

struct NewTemplate: Codable {
    let userId: Int
    let categoryId: Int
    let isPublic: Bool
    let templateRequirements: [TemplateRequirement]

    enum CodingKeys: String, CodingKey {
        case userId = "user_id"
        case categoryId = "category_id"
        case isPublic = "is_public"
        case templateRequirements = "template_requirements"
    }
}




//struct Requirement: Identifiable, Codable {
//    let id: Int
//    let requirementName: String
//    let requirementValue: String?
//
//    enum CodingKeys: String, CodingKey {
//        case id = "requirement_id"
//        case requirementName = "requirement_name"
//        case requirementValue = "requirement_value"
//    }
//}




struct HistoryItem: Identifiable, Decodable, Hashable {
    let id: Int
    let userId: Int
    let templateId: Int
    let usedAt: String
    let businessId: Int
    let businessBusinessName: String
    let status: String?

    enum CodingKeys: String, CodingKey {
        case id
        case userId = "user_id"
        case templateId = "template_id"
        case usedAt = "used_at"
        case businessId = "business_id"
        case businessBusinessName = "business_business_name"
        case status
    }
}


struct Business: Codable {
    let id: Int
    let userId: Int
    let businessName: String
    let address: String
    let category: Category
    let createdAt: String
    let updatedAt: String

    enum CodingKeys: String, CodingKey {
        case id
        case userId = "user_id"
        case businessName = "business_name"
        case address
        case category
        case createdAt = "created_at"
        case updatedAt = "updated_at"
    }
}

struct Category: Codable {
    let id: Int
    let categoryName: String

    enum CodingKeys: String, CodingKey {
        case id
        case categoryName = "category_name"
    }
}

struct Template: Codable, Identifiable {
    let id: Int
    let userId: Int
    let categoryId: Int
    let createdAt: String
    let updatedAt: String
    let isPublic: Bool
    let reviewIds: [Int]
    let templateRequirements: [Requirement]
    let userTemplateHistoryIds: [Int]
    let status: String

    enum CodingKeys: String, CodingKey {
        case id
        case userId = "user_id"
        case categoryId = "category_id"
        case createdAt = "created_at"
        case updatedAt = "updated_at"
        case isPublic = "is_public"
        case reviewIds = "review_ids"
        case templateRequirements = "template_requirements"
        case userTemplateHistoryIds = "user_template_history_ids"
        case status
    }
}

struct Requirement: Codable, Hashable {
    let requirementName: String
    let requirementValue: String

    enum CodingKeys: String, CodingKey {
        case requirementName = "requirement_name"
        case requirementValue = "requirement_value"
    }
}
