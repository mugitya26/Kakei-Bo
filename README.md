# Kakei-Bo

## 概要

本アプリは、レシートの写真から購入した商品と価格を自動で読み込み、素早く家計簿を作成することで利用者の負担を軽減することを目的として開発しています。

## 実行方法

まず、[IntelliJ IDEA](https://www.jetbrains.com/ja-jp/idea/download/)のプロジェクト選択画面で`README.md`や`pom.xml`が配置されているディレクトリを選択して本プロジェクトを開いてください。

次に、`src/main/java/adv1b/group06/kakeibo/Kakeibo.java`を実行してください。

依存パッケージが存在しないことなどでエラーなどが発生してアプリが正常に動作しない場合には、`Ctrl Shift A`を押して`Maven Reload`と検索する。そして、候補として示された`すべてのMavenプロジェクトを再ロード`を選択してください。これにより、依存ファイルが一括でダウンロードされます。

### 注意

- パスに日本語などのUnicode文字が含まれている場合にOCR機能を利用すると、tess4jのバグで**アプリがクラッシュ**します。
- 解像度の高い画像を読み込むと、一時的にアプリの応答がなくなりますが、数十秒で何らかの応答が得られます。この時、エラーが発生した場合にはエラーメッセージが出力される仕様です。
- 読み込むレシート画像は、次の要件を満たしていることを想定しています。
  - レシートの外に無関係な文字が無いこと
  - レシートが中央に真っ直ぐ配置されていること
  - 画像から文字が読み取り可能な明度、解像度であること
  - 芝浦工業大学の生協もしくは学食のレシートであること


- カテゴリーの自動振り分け機能の利用には[OpenAIのAPI key](https://platform.openai.com/account/api-keys)が必要です(有料)。
- API keyは実行ファイルのあるディレクトリにkeyというファイルを作成してその中に記述してください。
- API keyが設定されていない場合、自動振り分けは未割当となります。

## データの保存先

OCRに利用できるサンプル画像はプロジェクトのルートディレクトリ直下の`images`フォルダ内に配置されています。

家計簿データを保存するJSONファイルについては同じくルート直下の`data`フォルダに配置されます。

Tesseractに利用する学習データについては、`src/main/resources/adv1b/group06/kakeibo/traineddata`に配置されているため、個人の環境で学習データをダウンロードして**環境変数を通す必要はないです**。

## ビルド方法
IntelliJ IDEAでプロジェクトをインポートします。その後、`File>Project Structure>Project Settings>Artifacts`で設定画面を開きます。
+マークを押して、`JAR>From modules with dependencies...`を押して、Main ClassにはKakeiboを設定します。
Jar files from librariesはextract to the target JARを選択してOKを押します。Applyしてから設定画面を閉じます。


`Build>Build Artifacts...>Kakeibo:jar>ReBuild`を実行すると、`out\artifacts\Kakeibo_jar\Kakeibo.jar`が生成されます。
通常のjarファイルと同様`java -jar Kakeibo.jar`で起動できます。
