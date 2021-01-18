package com.valjapan.clipper

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@AssistedModule
@Module
class AssistedInjectModule