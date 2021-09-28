package io.fajarca.project.jetnews.util.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.fajarca.project.jetnews.domain.entity.Article

class ArticleProvider : PreviewParameterProvider<Article> {
    override val values: Sequence<Article>
        get() = sequenceOf(
            Article(
                "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa",
                "2021-09-23T05:55:54Z",
                "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa - Kontan",
                "https://lifestyle.kontan.co.id/news/duh-bug-ios-15-menganggap-ruang-penyimpanan-penuh-meskipun-masih-ada-sisa",
                "https://foto.kontan.co.id/H3LwljVMcQdeUbi8U_XTzM-v8T0=/smart/2020/10/14/963412751p.jpg",
                "Kontan.co.id",
                false
            )
        )
    override val count: Int
        get() = values.count()
}