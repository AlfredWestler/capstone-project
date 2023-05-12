package com.asgh.themoviedb.presentation.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.asgh.themoviedb.R
import com.asgh.themoviedb.commons.either.FailureModel
import com.asgh.themoviedb.commons.either.TMDBEither
import com.asgh.themoviedb.domain.model.TMDBLatestMovie
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.domain.use_case.TMDBLatestUseCase
import com.asgh.themoviedb.domain.use_case.TMDBNowPlayingUseCase
import com.asgh.themoviedb.domain.use_case.TMDBTopRatedUseCase
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardViewModel
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@OptIn(ExperimentalCoroutinesApi::class)
@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(AndroidJUnit4::class)
class TMDBDashboardViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val nowPlayingUseCase = mockk<TMDBNowPlayingUseCase>(relaxed = true)
    private val topRatedUseCase = mockk<TMDBTopRatedUseCase>(relaxed = true)
    private val latestUseCase = mockk<TMDBLatestUseCase>(relaxed = true)
    private val sut = TMDBDashboardViewModel(dispatcher, nowPlayingUseCase, topRatedUseCase, latestUseCase)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `poner un titulo al toolbar`() {
        //given
        val title = "titulo del toolbar"
        //when
        sut.setToolbarTitle(title)

        //then
        val titleLiveData = sut.toolbarTitle.value
        titleLiveData?.let {
            Truth.assertThat(it).isInstanceOf(String::class.java)
            Truth.assertThat(it).isEqualTo(title)
        }
    }

    @Test
    fun `obtener el valor del verificador de internet como verdadero`() {
        //given
        val hasInternetConnection = true
        //when
        sut.setInternetConnectionEnabled(hasInternetConnection)
        //then
        val internetState = sut.internetConnectionEnabled.value
        internetState.let {
            Truth.assertThat(it).isEqualTo(hasInternetConnection)
        }
    }

    @Test
    fun `obtener las peliculas de la categoria now playing, top rated y latest, responde success todos`() {
        //given
        val nowPlayingList = listOf(TMDBMovie())
        val topRatedList = listOf(TMDBMovie())
        val latest = TMDBLatestMovie()
        coEvery { nowPlayingUseCase.getNowPlayingMoviesModified() } returns flow {
            emit(TMDBEither.Success(nowPlayingList))
        }
        coEvery { topRatedUseCase.getTopRatedMoviesAsFlow() } returns flow {
            emit(TMDBEither.Success(topRatedList))
        }
        coEvery { latestUseCase.getLatestAsFlow() } returns flow {
            emit(TMDBEither.Success(latest))
        }
        //when
        sut.setInternetConnectionEnabled(true)
        //then
        val nowPlayingState = sut.nowPlayingState.value
        val topRatedSate = sut.topRatedState.value
        val latestState = sut.latestState.value
        nowPlayingState.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Success(nowPlayingList))
        }
        topRatedSate.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Success(topRatedList))
        }
        latestState.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Success(latest))
        }
    }

    @Test
    fun `obtener las peliculas de la categoria now playing, top rated y latest, responden loading todos`() {
        //given
        coEvery { nowPlayingUseCase.getNowPlayingMoviesModified() } returns flow {
            emit(TMDBEither.Loading)
        }
        coEvery { topRatedUseCase.getTopRatedMoviesAsFlow() } returns flow {
            emit(TMDBEither.Loading)
        }
        coEvery { latestUseCase.getLatestAsFlow() } returns flow {
            emit(TMDBEither.Loading)
        }
        //when
        sut.setInternetConnectionEnabled(true)
        //then
        val nowPlayingState = sut.nowPlayingState.value
        val topRatedSate = sut.topRatedState.value
        val latestState = sut.latestState.value
        nowPlayingState.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Loading)
        }
        topRatedSate.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Loading)
        }
        latestState.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Loading)
        }
    }

    @Test
    fun `obtener las peliculas de la categoria now playing, top rated y latest, responden failure todos`() {
        //given
        coEvery { nowPlayingUseCase.getNowPlayingMoviesModified() } returns flow {
            emit(TMDBEither.Failure(""))
        }
        coEvery { topRatedUseCase.getTopRatedMoviesAsFlow() } returns flow {
            emit(TMDBEither.Failure(""))
        }
        coEvery { latestUseCase.getLatestAsFlow() } returns flow {
            emit(TMDBEither.Failure(""))
        }
        //when
        sut.setInternetConnectionEnabled(true)
        //then
        val nowPlayingState = sut.nowPlayingState.value
        val topRatedSate = sut.topRatedState.value
        val latestState = sut.latestState.value
        nowPlayingState.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Failure(FailureModel(R.string.generic_error_message)))
        }
        topRatedSate.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Failure(FailureModel(R.string.generic_error_message)))
        }
        latestState.let {
            Truth.assertThat(it).isInstanceOf(TMDBEither::class.java)
            Truth.assertThat(it).isEqualTo(TMDBEither.Failure(FailureModel(R.string.generic_error_message)))
        }
    }
}