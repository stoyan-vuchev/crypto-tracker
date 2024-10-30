package com.stoyanvuchev.cryptotracker.crypto.presentation.coins_list_screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stoyanvuchev.cryptotracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListScreen(
    screenState: CoinsListScreenState,
    onUIAction: (CoinsListScreenUIAction) -> Unit
) {

    val pullToRefreshState = rememberPullToRefreshState()
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            LargeTopAppBar(
                title = {

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.app_name)
                    )

                },
                scrollBehavior = scrollBehavior
            )

        }
    ) { safePadding ->

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            isRefreshing = screenState.isLoading,
            onRefresh = { onUIAction(CoinsListScreenUIAction.LoadCoins) },
            state = pullToRefreshState,
            indicator = {

                Indicator(
                    modifier = Modifier
                        .padding(safePadding)
                        .align(Alignment.TopCenter),
                    isRefreshing = screenState.isLoading,
                    state = pullToRefreshState
                )

            }
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                state = lazyListState,
                contentPadding = safePadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item { Spacer(modifier = Modifier.height(16.dp)) }

                items(
                    items = screenState.coins,
                    itemContent = { coin ->

                        CoinsListScreenItem(
                            modifier = Modifier
                                .animateItem(
                                    fadeInSpec = tween(),
                                    fadeOutSpec = tween()
                                )
                                .padding(horizontal = 16.dp),
                            coin = coin,
                            onUIAction = onUIAction
                        )

                    }
                )

                item { Spacer(modifier = Modifier.height(128.dp)) }

            }

        }

    }

}