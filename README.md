# Kakei-Bo

## 概要

　本アプリは、レシートの写真から購入した商品と価格を自動で読み込み、素早く家計簿を作成することで利用者の負担を軽減することを目的として開発しています。

## 実行方法

まず、[IntelliJ IDEA Community](https://www.jetbrains.com/ja-jp/idea/download/)のプロジェクト選択画面で`README.md`や`pom.xml`が配置されているディレクトリを選択して本プロジェクトを開く。

次に、`src/main/java/adv1b/group06/kakeibo/Kakeibo.java`を実行する。

依存パッケージが存在しないことなどでエラーなどが発生して、アプリが正常に動作しない場合には、一度、`Ctrl Shift A`を押して`Maven Reload`と検索する。そして、候補として示された`すべてのMavenプロジェクトを再ロード`を選択する。これにより、依存ファイルが一括でダウンロードされる。

## データの保存先

サンプル画像はプロジェクトのルートディレクトリ直下の`images`フォルダ内に配置されている。

家計簿データを保存するJSONファイルについては同じくルート直下の`data`フォルダに配置される。

Tesseractに利用する学習データについては、`src/main/resources/adv1b/group06/kakeibo/traineddata`に配置されているため、個人の環境で学習データをダウンロードして**環境変数を通す必要はない**。
