

## To use your captcha library in a project, you can follow these steps:

<br>

<b>1.</b> Add the library to your project: You can add your captcha library to your project by including the library JAR file in your project's classpath. The JAR file should contain all the necessary classes and resources.

<br>

<b>2.</b> Generate a captcha text: To generate a captcha text, create a new Captcha object and call the build() method. For example:

<br>

```java
Captcha captcha = new Captcha(Captcha.CaptchaType.MIX, 5);
String captchaText = captcha.build();
```
This will create a Captcha object with a mix of digits, uppercase and lowercase letters, and a length of 5 characters. The build() method will generate a random captcha text.

<br>

<b>3.</b> Generate a captcha image: To generate a captcha image from the captcha text, call the generateImage() method of the ImageCreator class. For example:

<br>

```java
File captchaImage = ImageCreator.generateImage(captchaText);
```
This will create a PNG image file with the captcha text drawn on it, with noise added to make it harder to read.

<br>

<b>4.</b> Display the captcha image: Display the captcha image to the user, either by embedding it in an HTML page or by serving it as a standalone image. You can use the following HTML code to embed the image:

<br>

```html
<img src="https://github.com/a8kj7sea/Captcha/blob/main/captcha.png" alt="Captcha image">
```
Replace path/to/captcha.png with the path to the captcha image file generated in step 3.

<br>

<b>5.</b> Validate the captcha text: When the user submits the form, validate the captcha text entered by the user with the captcha text generated in step 2. For example:

<br>

```java
if (userInput.equals(captchaText)) {
    // Captcha is valid
} else {
    // Captcha is invalid
}
```
This will compare the user's input to the captcha text generated in step 2. If they match, the captcha is valid and the form can be submitted. Otherwise, the captcha is invalid and the user will need to try again.

<br>

I hope this helps you use your captcha library in your projects.


