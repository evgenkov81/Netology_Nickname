import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        ExecutorService nicknameThreeLetter = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        AtomicInteger three = new AtomicInteger(0);
        IntStream.range(0, texts.length)
                .forEach(i -> nicknameThreeLetter.submit(() -> {
                    if (texts[i].length() == 3 && (isPalindrome(texts[i]) || sortedText(texts[i]) || sortedLetter(texts[i]))) {
                        three.addAndGet(1);
                    }
                }));
        nicknameThreeLetter.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Beautiful words with length 3: " + three.get() + " pc.");
        nicknameThreeLetter.shutdown();


        ExecutorService nicknameFourLetter = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        AtomicInteger four = new AtomicInteger(0);
        IntStream.range(0, texts.length)
                .forEach(i -> nicknameFourLetter.submit(() -> {
                    if (texts[i].length() == 4 && (isPalindrome(texts[i]) || sortedText(texts[i]) || sortedLetter(texts[i]))) {
                        four.addAndGet(1);
                    }
                }));

        nicknameFourLetter.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Beautiful words with length 4: " + four.get() + " pc.");
        nicknameFourLetter.shutdown();

        ExecutorService nicknameFiveLetter = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        AtomicInteger five = new AtomicInteger(0);
        IntStream.range(0, texts.length)
                .forEach(i -> nicknameFiveLetter.submit(() -> {
                    if (texts[i].length() == 5 && (isPalindrome(texts[i]) || sortedText(texts[i]) || sortedLetter(texts[i]))) {
                        four.addAndGet(1);
                    }
                }));

        nicknameFiveLetter.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Beautiful words with length 5: " + four.get() + " pc.");
        nicknameFiveLetter.shutdown();
    }


    public static boolean sortedText(String text) {

        char[] sText = text.toCharArray();
        Arrays.sort(sText);
        String sorted = String.valueOf(sText);

        return text.equals(sorted);

    }

    public static boolean sortedLetter(String text) {
        char[] sLetter = text.toCharArray();
        int nLetter = 0;
        boolean result = false;
        for (char i = 0; i < sLetter.length - 1; i++) {
            if (sLetter[i] == sLetter[i + 1])
                nLetter++;
        }
        if (nLetter == sLetter.length - 1) {
            result = true;
        }
        return result;
    }


    public static boolean isPalindrome(String text) {
        return text.equalsIgnoreCase(new StringBuilder(text)
                .reverse().toString());

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
