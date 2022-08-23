package com.github.oauth.repositories.githuboauthreposview.view.forks

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.oauth.repositories.githuboauthreposview.di.scope.containers.ForksScopeContainer
import com.github.oauth.repositories.githuboauthreposview.domain.GithubCommitRepository
import com.github.oauth.repositories.githuboauthreposview.domain.UserChooseRepository
import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
import com.github.oauth.repositories.githuboauthreposview.utils.*
import com.github.oauth.repositories.githuboauthreposview.utils.connectivity.NetworkStatus
import com.github.oauth.repositories.githuboauthreposview.utils.imageloader.ImageLoader
import com.github.oauth.repositories.githuboauthreposview.utils.resources.ResourcesProvider
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class ForksPresenter @Inject constructor(
    private val router: Router,
    private val networkStatus: NetworkStatus,
    private val forksScopeContainer: ForksScopeContainer,
    private val userChoose: UserChooseRepository,
    private val commit: GithubCommitRepository,
    private val resourcesProvider: ResourcesProvider,
    private val glide: ImageLoader<ImageView>
): MvpPresenter<ForksView>() {
    /* Исходные данные */ //region
    private var file: File = File("")
    //endregion

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    fun onCommitClicked(commit: GithubCommitModel) {
        userChoose.setGithubCommitModel(commit)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData(userChoose.getGithubRepoModel().id)
    }

    private fun loadData(repoId: Int) {
        commit.getCommits(repoId, viewState)
    }

    /** Уничтожение ForksSubcomponent при уничтожении данного презентера */
    override fun onDestroy() {
        forksScopeContainer.destroyForksSubcomponent()
        super.onDestroy()
    }

    fun loadAvatar(url: String, container: ImageView) {
        if ((networkStatus.isOnline() &&
            (userChoose.getResponseCode() != ServerResponseStatusCode.CLIENT_ERROR)) &&
            (userChoose.getNumberRemainingRequest() > 0) &&
            (!userChoose.getIsAvatarUpdated())) {
            glide.loadInto(url, container)
            file = File(
                "${resourcesProvider.getContext().
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                }/$IMAGE_CACHE_FOLDER_NAME/${userChoose.getGithubUserModel().login}"
            )

            /** Сохранение картинки в локальную папку с данным приложением */
            /** Создание директории, если она ещё не создана */
            if (!file.exists()) {
                file.mkdirs()
            }
            file = File(file, "${userChoose.getGithubUserModel().login}.$IMAGE_FORMAT")
            /** Создание файла */
            file.createNewFile()

            CoroutineScope(Dispatchers.IO).launch {
                saveImage(
                    Glide.with(resourcesProvider.getContext())
                        .asBitmap()
                        .load(url)
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .submit()
                        .get(), file
                )
            }
            // Сохранение признака обновления аватара пользователя
            userChoose.setIsAvatarUpdated(true)
        } else {
            file = File(
                "${resourcesProvider.getContext().
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                }/$IMAGE_CACHE_FOLDER_NAME/${userChoose.getGithubUserModel().login}"
            )
            file = File(file, "${userChoose.getGithubUserModel().login}.$IMAGE_FORMAT")
            if ((file.exists()) && (file.length() > 0)) {
                glide.loadInto(file.toString(), container)
            }
        }
    }

    /** Сохранение картинки */
    private fun saveImage(image: Bitmap, outPutFile: File) {
        try {
            val fOut: OutputStream = FileOutputStream(outPutFile)
            image.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, fOut)
            fOut.close()
        } catch (e: Exception) {
            Log.d(LOG_TAG, "${e.message}")
        }
    }
}