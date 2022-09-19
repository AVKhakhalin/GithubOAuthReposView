# GithubOAuthReposView
<h1>Описание по-русски:</h1>

_Назначение приложения_
-----------------------
Приложение "GithubOAuthReposView" - мобильный клиент для просмотра информации о коммитах в открытых и закрытых репозиториях пользователя Github с авторизацией через OAuth, отслеживанием доступа к серверам OAuth и github.com.
Репозитории пользователя и его коммиты представлены в виде списков.

В приложении реализованы уведомления о следующих ошибках:
1. Отсутствие сети Интернет:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191009982-a0b417ba-0845-461c-86e0-99256c1329cc.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191009969-cfb686c9-eef2-4ec6-9c12-9fde9e3ed15e.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191012879-f82d327c-4959-41bb-9feb-9b3a5e051217.jpg">
</p>

2. Нет доступа к сайту с OAuth:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191011811-1f73e3fe-dabc-4fac-9b6e-8b706cb2a260.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191011857-9d3b01c7-723c-4d95-bf9c-867229a93868.jpg">
</p>

3. Нет доступа к сайту github.com:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191011692-2268cb3e-62c5-4cac-8372-82b87a349076.jpg">
</p>


_Описание работы приложения_
-------------------------------------------------------

Главное окно приложения представлено тремя кнопками: 
1. "Авторизация в GitHub".

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191012962-5c7187df-908e-4af0-bd8b-e30fc2fc4580.jpg">
</p>

2. "Сменить пользователя в GitHub".
3. "Просмотреть репозитории AVKhakhalin в Github".

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191013011-d470f1ad-4545-40e0-8667-d43013dbea9a.jpg">
</p>

Кнопка 1 появляется, когда пользователь только зашёл в приложение или вышел из своей учётной сессии.
В этот момент кнопки 2-3 не видны.
Когде же OAuth авторизация пользователя состоялась, то кнопка 1 пропадает, а кнопки 2-3 появляются.

OAuth авторизация запускается при нажатии на кнопку 1.

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191013277-9db9696e-b65f-4466-9870-c7ab410ef572.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191013949-774c7110-f994-43cb-ad61-ce950d8cf369.jpg">
</p>

Она реализована через элемент WebView.
После ввода корректных логина и пароля происходит возвращение к кнопкам 2-3.
Если в процессе авторизации происходит непредвиденная ошибка, то нужно нажать на верхнюю кнопку "Перезапустить авторизацию OAuth".
Для выхода из своей учётной сесии, нужно нажать на кнопку 2. В этом случае появится окно:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191014521-5efd60f1-4030-44e2-b9d6-8514c483332a.jpg">
</p>

Для завершения выхода из своей сессии нужно нажать на кнопку "Sign out".
Выход из своей сессии также реализован с помощью элемента WebView.

Для получения списка своих репозиториев нужно нажать на кнопку 3:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191015075-5b04e425-ec96-473a-9c1c-c969cd09f3fc.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191015096-5e9845f5-28e7-4151-9bc5-b1b9bf9bcbd7.jpg">
</p>

Репозитории упорядочены по алфавиту. Список можно прокрутить вниз или вверх для поиска нужного репозитория.
Далее, нужно на найденный репозиторий кликнуть.
После этого загрузится список коммитов данного репозитория, если он есть.
Если коммитов в репозитории нет, то появится сообщение: "Нет коммитов в репозитории":

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191016559-05b437d3-7bfd-43d4-bea7-299f688a07a6.jpg">
</p>

Отсутствие коммитов или данного сообщения говорит о том, что информация загружается. Также об этом говорит крутящийся элемент ProgressBar:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191017181-6760a193-cb0b-463c-bacd-923f9147e364.jpg">
</p>

В верхней части окно с информацией о коммитах появляется следующая информация:
1. Название репозитория.
2. Описание репозитория.
3. Логотип автора репозитория.
4. Количество "форков".
5. Количество просмотров репозитория.

Репозитории отсортированы по дате: коммит с самой ранней датой отображается вверху, а коммит с самой поздней датой - внизу:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191017912-0fb7f265-81ad-4fb1-b44d-ffc4b3f80f1d.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191017931-ce92487e-53e9-4a43-8113-454eee570a46.jpg">
</p>

У любого коммита можно выделить и скопировать в буфер любую выведенную информацию:
1. Дата.
2. Хеш-код.
3. Описание коммита.

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191018622-af542331-fe90-4d12-b6b9-757c3842ef20.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191018651-de1731fd-f343-43a5-b18e-1aeb70c4cf8e.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191018672-db5f0fbb-691b-48cd-aa75-96cd8bd794df.jpg">
</p>


_Архитектурные и технологические решения_
-----------------------
- IDE: Android Studio Bumblebee 2021.1.1
- Kotlin
- Dagger
- RxJava 3
- MVP
- Moxy
- Coroutines
- Room
- Glide
- Retrofit
- Delegate
- Cicerone
- OkHttp
- Interceptor
- CopyOnWriteArrayList
- OAuth
- PHP
- Clean Architecture
- WebView
- ViewBinding
- ConstraintLayout + RecyclerView + AppCompatImageView + ProgressBar + AppCompatTextView + AppCompatButton + TextView + View + Xml
- dependencies.gradle
- Extension functions

_Установка_
------------
- Приложение разработано под Android.
- Требования к SDK: минимум - 21, максимум - 32

-------------------------------------------------

<h1>Description in English:</h1>

_Application purpose_
-----------------------
The GithubOAuthReposView application is a mobile client for viewing information about commits in open and closed repositories of a Github user with authorization via OAuth, tracking access to OAuth servers and github.com .
The user's repositories and commits are presented in the form of lists.

Notifications about the following errors are implemented in the application:
1. Lack of Internet:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191009982-a0b417ba-0845-461c-86e0-99256c1329cc.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191009969-cfb686c9-eef2-4ec6-9c12-9fde9e3ed15e.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191012879-f82d327c-4959-41bb-9feb-9b3a5e051217.jpg">
</p>

2. There is no access to the site with OAuth:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191011811-1f73e3fe-dabc-4fac-9b6e-8b706cb2a260.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191011857-9d3b01c7-723c-4d95-bf9c-867229a93868.jpg">
</p>

3. There is no access to the site github.com:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191011692-2268cb3e-62c5-4cac-8372-82b87a349076.jpg">
</p>


_Description of the application_
-------------------------------------------------------

The main application window is represented by three buttons:
1. "Authorization in GitHub".

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191012962-5c7187df-908e-4af0-bd8b-e30fc2fc4580.jpg">
</p>

2. "Change user in GitHub".
3. "View AVKhakhalin repositories in Github".

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191013011-d470f1ad-4545-40e0-8667-d43013dbea9a.jpg">
</p>

Button 1 appears when the user has just logged into the application or logged out of their account session.
At this moment, buttons 2-3 are not visible.
When the user's OAuth authorization has taken place, button 1 disappears, and buttons 2-3 appear.

OAuth authorization starts when you click on button 1.

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191013277-9db9696e-b65f-4466-9870-c7ab410ef572.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191013949-774c7110-f994-43cb-ad61-ce950d8cf369.jpg">
</p>

It is implemented through the WebView element.
After entering the correct login and password, the user returns to buttons 2-3.
If an unexpected error occurs during the authorization process, then you need to click on the top button "Restart OAuth authorization".
To log out of your account session, you need to click on button 2. In this case, a window will appear:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191014521-5efd60f1-4030-44e2-b9d6-8514c483332a.jpg">
</p>

To complete the exit from your session, you need to click on the "Sign out" button.
Exiting your session is also implemented using the WebView element.

To get a list of your repositories, click on button 3:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191015075-5b04e425-ec96-473a-9c1c-c969cd09f3fc.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191015096-5e9845f5-28e7-4151-9bc5-b1b9bf9bcbd7.jpg">
</p>

Repositories are arranged alphabetically. The list can be scrolled down or up to find the desired repository.
Next, you need to click on the found repository.
After that, the list of commits of this repository will be loaded, if there is one.
If there are no commits in the repository, a message will appear: "No commits in the repository":

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191016559-05b437d3-7bfd-43d4-bea7-299f688a07a6.jpg">
</p>

The absence of commits or this message indicates that the information is being downloaded. This is also indicated by the rotating ProgressBar element:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191017181-6760a193-cb0b-463c-bacd-923f9147e364.jpg">
</p>

The following information appears at the top of the comments window:
1. The name of the repository.
2. Description of the repository.
3. The logo of the repository author.
4. The number of "forks".
5. The number of repository views.

Repositories are sorted by date: the commit with the earliest date is displayed at the top, and the commit with the latest date is displayed at the bottom:

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191017912-0fb7f265-81ad-4fb1-b44d-ffc4b3f80f1d.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191017931-ce92487e-53e9-4a43-8113-454eee570a46.jpg">
</p>

You can select any output information from any commit and copy it to the buffer.:
1. Date.
2. Hash code.
3. Description of the commit.

<p align="center">
<img  src="https://user-images.githubusercontent.com/78661461/191018622-af542331-fe90-4d12-b6b9-757c3842ef20.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191018651-de1731fd-f343-43a5-b18e-1aeb70c4cf8e.jpg">
<img  src="https://user-images.githubusercontent.com/78661461/191018672-db5f0fbb-691b-48cd-aa75-96cd8bd794df.jpg">
</p>


_Architectural and technological solutions_
-----------------------
- IDE: Android Studio Bumblebee 2021.1.1
- Kotlin
- Dagger
- RxJava 3
- MVP
- Moxy
- Coroutines
- Room
- Glide
- Retrofit
- Delegate
- Cicerone
- OkHttp
- Interceptor
- CopyOnWriteArrayList
- OAuth
- PHP
- Clean Architecture
- WebView
- ViewBinding
- ConstraintLayout + RecyclerView + AppCompatImageView + ProgressBar + AppCompatTextView + AppCompatButton + TextView + View + Xml
- dependencies.gradle
- Extension functions

_Installation_
------------
- The application is developed for Android.
- SDK requirements: minimum - 21, maximum - 32
