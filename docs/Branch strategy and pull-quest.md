# `브랜치 전략과 풀 리퀘스트` 

## `1. 브랜치 전략`

먼저 코드의 충돌나는 상황을 최대한 적게 하고 프로젝트를 배포할 때 수월하게 하기 위해서는 브랜치 관리를 잘하는 것이 중요합니다. 

따라서 아래와 같이 브랜치를 관리하려고 합니다. 

<br>

### `main 브랜치`

- develop 브랜치에서 배포할 수 있을 정도로 구현된 것을 Merge를 하는 브랜치입니다. 

<br>

### `develop 브랜치`

- 기능 개발과 버그 수정의 브랜치 Merge가 자주 일어나는 브랜치입니다. 한마디로 개발이 활발하게 일어나는 브랜치입니다. 
- 되도록이면 개발을 할 때 develop 브랜치에서 새로운 브랜치를 생성합니다.
<br>

### `issue`

- 이슈를 만들 때 issue 탬플릿을 사용하여 이슈의 내용과 체크리스트를 작성하고 제목은 추가하려는 기능을 간략하게 나타냅니다.  ex) PDF View 추가
- 이슈에 맞는 적절한 Label을 지정하고 Project에서 PDF_Viewer_Client를 선택합니다.
  
<img width="795" alt="image" src="https://user-images.githubusercontent.com/41673190/163109215-a99b4d4e-6b74-4f75-9799-978ed27bbb9d.png">
<br>

### `feature(Label)/#이슈번호`


- 본인이 이슈를 만들었던(기능) 것에 대한 기능을 개발하는 브랜치입니다. 기능이 완벽하게 구현이 되었다면 develop 브랜치에 Pull Request를 보낸 후에 Merge를 하면 됩니다. ex) feature/#1

<br>

## `Pull Request`

Pull Request를 보내는 이유는 Merge 하기 전에 `코드 리뷰`를 하기 위해서 입니다. 
만약 `feature/#1`과 같은 기능 브랜치가 완성이 되었다면 `develop` 브랜치에 Pull Request를 보내야 합니다.