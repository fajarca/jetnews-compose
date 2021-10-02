package io.fajarca.project.jetnews.util.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.fajarca.project.jetnews.presentation.list.ArticleUiModel
import io.fajarca.project.jetnews.util.date.TimeDifference

class ArticleProvider : PreviewParameterProvider<ArticleUiModel> {
    override val values: Sequence<ArticleUiModel>
        get() = sequenceOf(
            ArticleUiModel(
                "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa",
                "2021-09-23T05:55:54Z",
                "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa - Kontan",
                "https://lifestyle.kontan.co.id/news/duh-bug-ios-15-menganggap-ruang-penyimpanan-penuh-meskipun-masih-ada-sisa",
                "https://foto.kontan.co.id/H3LwljVMcQdeUbi8U_XTzM-v8T0=/smart/2020/10/14/963412751p.jpg",
                false,
                TimeDifference.Day(4)
            )
        )
    override val count: Int
        get() = values.count()
}