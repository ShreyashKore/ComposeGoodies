/*
 * Copyright (c) 2022. Shreyash Kore.
 */

package com.shreyashkore.library.pager

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@ExperimentalPagerApi
val PagerState.isOnLastPage: Boolean
    get() = this.currentPage + 1 == this.pageCount || this.targetPage + 1 == this.pageCount

@ExperimentalPagerApi
val PagerState.isOnFirstPage: Boolean
    get() = this.currentPage == 0 || this.targetPage == 0