package com.example.gitgraphdemo.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import com.apollographql.apollo.sample.GetGitInfoQuery
import com.example.gitgraphdemo.model.GitProfileInfo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface GitDataRepository {
    fun getUserGitInfo(ownerName: String): Single<GitProfileInfo>
}

class GitDataRepositoryImpl(private val apolloClient: ApolloClient) : GitDataRepository {

    private val emptyInfo = GitProfileInfo("", "", "", 0)

    override fun getUserGitInfo(ownerName: String): Single<GitProfileInfo> {

        val apolloQuery = GetGitInfoQuery.builder().owner(ownerName).build()
        val apolloCall = apolloClient.query(apolloQuery)
        return Rx2Apollo.from(apolloCall)
            .firstOrError()
            .observeOn(Schedulers.io()).map {
                val result = it.data()?.user()
                if (result == null) {
                    emptyInfo
                } else {
                    GitProfileInfo(result.name().orEmpty(), result.bio().orEmpty(), result.location().orEmpty(), result.starredRepositories().totalCount())
                }
            }
    }
}

