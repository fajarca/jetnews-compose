package io.fajarca.project.jetnews.util.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.fajarca.project.jetnews.presentation.list.ArticleUiModel
import io.fajarca.project.jetnews.util.date.TimeDifference

class ArticleProvider : PreviewParameterProvider<ArticleUiModel> {
    override val values: Sequence<ArticleUiModel>
        get() = sequenceOf(
            ArticleUiModel(
                "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa",
                "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa",
                "https://lifestyle.kontan.co.id/news/duh-bug-ios-15-menganggap-ruang-penyimpanan-penuh-meskipun-masih-ada-sisa",
                "https://lifestyle.kontan.co.id/news/duh-bug-ios-15-menganggap-ruang-penyimpanan-penuh-meskipun-masih-ada-sisa",
                "Kompas.co.id",
                false,
                TimeDifference.Day(4)
            )
        )
    override val count: Int
        get() = values.count()
}