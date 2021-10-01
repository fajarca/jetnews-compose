package io.fajarca.project.jetnews.util.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import java.util.*

class SearchHistoryProvider : PreviewParameterProvider<SearchHistory> {
    override val values: Sequence<SearchHistory>
        get() = sequenceOf(
            SearchHistory("Aston Martin", Date())
        )

    override val count: Int
        get() = values.count()
}