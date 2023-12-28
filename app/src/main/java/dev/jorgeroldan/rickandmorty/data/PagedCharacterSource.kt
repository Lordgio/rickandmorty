package dev.jorgeroldan.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.jorgeroldan.rickandmorty.GetCharactersQuery
import dev.jorgeroldan.rickandmorty.features.list.CharacterGender
import dev.jorgeroldan.rickandmorty.features.list.CharacterMainInfo
import dev.jorgeroldan.rickandmorty.features.list.toCharacterMainInfo
import dev.jorgeroldan.rickandmorty.fragment.CharacterShortDetail

class PagedCharacterSource(
    private val repository: CharacterRepository,
    private val filter: CharacterGender?
) : PagingSource<Int, CharacterMainInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterMainInfo> {
        val page = params.key ?: 1
        return repository.getCharacters(page, filter).fold(
            ifLeft = ::handleLoadError,
            ifRight = ::handleLoadSuccess
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterMainInfo>): Int? {
        val anchorPage = state.anchorPosition?.let { state.closestPageToPosition(it) }
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    private fun handleLoadError(error: Throwable): LoadResult<Int, CharacterMainInfo> {
        return LoadResult.Error(error)
    }

    private fun handleLoadSuccess(result: Pair<GetCharactersQuery.Info, List<CharacterShortDetail>>): LoadResult<Int, CharacterMainInfo> {
        val nextPage = result.first.next
        val previousPage = result.first.prev
        val items = result.second.map { it.toCharacterMainInfo() }
        return LoadResult.Page(
            data = items,
            prevKey = previousPage,
            nextKey = nextPage,
        )
    }
}
