import SwiftUI
import WebKit

//
// Created by Andrey Romanyuk on 04.01.2022.
// Copyright (c) 2022 orgName. All rights reserved.
//

struct HTMLStringView: UIViewRepresentable {
    let htmlContent: String

    func makeUIView(context: Context) -> WKWebView {
        WKWebView()
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        uiView.loadHTMLString(htmlContent, baseURL: nil)
    }
}