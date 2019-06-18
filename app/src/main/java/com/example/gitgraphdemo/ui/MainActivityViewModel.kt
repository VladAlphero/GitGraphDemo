package com.example.gitgraphdemo.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitgraphdemo.repository.GitDataRepository
import com.example.gitgraphdemo.model.GitProfileInfo


class MainActivityViewModel(private val gitDataRepository: GitDataRepository) : ViewModel() {
    companion object {
        const val TAG = "MainActivityViewModel"
    }
    private val gitInfo = MutableLiveData<GitProfileInfo>()
    private val isLoading = MutableLiveData<Boolean>()
    private val emptyInfo = GitProfileInfo("", "", "", 0)

    fun getGitInfo(): LiveData<GitProfileInfo> = gitInfo
    fun isLoading(): LiveData<Boolean> = isLoading

    @SuppressLint("CheckResult")
    fun findRepositories(login: String) {
        isLoading.postValue(true)

        gitDataRepository.getUserGitInfo(login).doOnEvent { _, _ ->
            isLoading.postValue(false)
        }.subscribe ({
            gitInfo.postValue(it)
        }, {
            Log.d(TAG, "error ${it.localizedMessage.orEmpty()}")
            gitInfo.postValue(emptyInfo)
        })
    }
}