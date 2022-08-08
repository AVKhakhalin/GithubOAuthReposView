package com.github.oauth.repositories.githuboauthreposview.domain.retrofit

//import com.github.oauth.repositories.githuboauthreposview.db.AppDatabase
//import com.github.oauth.repositories.githuboauthreposview.db.model.RoomGithubCommit
//import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitCommitInfoModel
//import com.github.oauth.repositories.githuboauthreposview.model.GithubCommitModel
//import com.github.oauth.repositories.githuboauthreposview.model.GithubUserModel
//import com.github.oauth.repositories.githuboauthreposview.model.RoomGithubCommitInfoAuthorModel
//import com.github.oauth.repositories.githuboauthreposview.remote.RetrofitService
//import io.reactivex.rxjava3.core.Single
//
//
//class GithubCommitRetrofitImpl(
//    private val retrofitService: RetrofitService,
//    private val db: AppDatabase
//): GithubCommitRetrofit {
//    override fun getRetrofitCommit(userModel: GithubUserModel, repoName: String
//    ): MutableList<GithubCommitModel> {
//        // Получение списка веток
//        val branchesList: MutableList<String> = mutableListOf()
//        val branchesUrl: String =
//            "https://api.github.com/repos/${userModel.login}/$repoName/branches"
//        retrofitService.getBranches(branchesUrl)
//            .map { branches ->
//                val branches = branches.map { branch ->
//                    branch.name
//                }
//                branches.forEach { branchesList.add(it) }
//            }
//        // Получение списка коммитов для каждой ветки
//        val commitsList: MutableList<RoomGithubCommit> = mutableListOf()
//        branchesList.forEach { branch ->
//            val commitsUrl: String =
//                "https://api.github.com/repos/${userModel.login}/$repoName/commits?sha=$branch"
//            retrofitService.getCommits(commitsUrl)
//                .flatMap { commits ->
//                    val dbCommits = commits.map {
//                        RoomGithubCommit(it.sha, repoName, it.owner.message,
//                        it.owner.author.name, it.owner.author.date)
//                    }
//                    dbCommits.forEach { commitsList.add(it) }
//                    db.commitDao.insert(dbCommits)
//                        .toSingle { commits }
//                }
//        }
//        // Получение результирующего списка
//        val githubCommitModel: MutableList<GithubCommitModel> = mutableListOf()
//        commitsList.forEach {
//            githubCommitModel.add(GithubCommitModel(it.id, GithubCommitCommitInfoModel(
//                it.message, RoomGithubCommitInfoAuthorModel(it.authorName, it.date))))
//        }
//        return githubCommitModel
//    }
//}