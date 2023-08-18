import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static String[] texts;

    public static AtomicInteger counter3 = new AtomicInteger();
    public static AtomicInteger counter4 = new AtomicInteger();
    public static AtomicInteger counter5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable palindrome = () -> {
            for (int i = 0; i < texts.length; i++) {
                boolean logic = true;
                List<Character> chars = texts[i].chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList());

                Set<Character> charsSet = texts[i].chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toSet());
                if (charsSet.size() != 1) {
                    for (int j = 0; j < chars.size(); j++) {
                        if (chars.get(j) != chars.get(chars.size() - j - 1)) {
                            logic = false;
                        }
                    }
                    if (logic) {
                        switch (chars.size()) {
                            case (3):
                                counter3.getAndIncrement();
                                break;
                            case (4):
                                counter4.getAndIncrement();
                                break;
                            case (5):
                                counter5.getAndIncrement();
                                break;
                        }
                    }
                }
            }
        };

        Runnable oneLetter = () -> {
            for (int i = 0; i < texts.length; i++) {
                List<Character> chars = texts[i].chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList());

                Set<Character> charsSet = texts[i].chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toSet());
                if (charsSet.size() == 1) {
                    switch (chars.size()) {
                        case (3):
                            counter3.getAndIncrement();
                            break;
                        case (4):
                            counter4.getAndIncrement();
                            break;
                        case (5):
                            counter5.getAndIncrement();
                            break;
                    }
                }
            }
        };

        Runnable alphabet = () -> {
            for (int i = 0; i < texts.length; i++) {
                boolean logic = true;
                List<Character> chars = texts[i].chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList());

                Set<Character> charsSet = texts[i].chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toSet());

                if (charsSet.size() != 1) {
                    for (int j = 0; j < chars.size() - 1; j++) {
                        if ((int) chars.get(j) > (int) chars.get(j + 1)) {
                            logic = false;
                        }
                    }
                }
                if (logic) {
                    switch (chars.size()) {
                        case (3):
                            counter3.getAndIncrement();
                            break;
                        case (4):
                            counter4.getAndIncrement();
                            break;
                        case (5):
                            counter5.getAndIncrement();
                            break;
                    }
                }
            }
        };

        Thread palindromThread = new Thread(palindrome);
        Thread oneLetterThread = new Thread(oneLetter);
        Thread alphabetThread = new Thread(alphabet);

        palindromThread.start();
        oneLetterThread.start();
        alphabetThread.start();

        palindromThread.join();
        oneLetterThread.join();
        alphabetThread.join();

        System.out.println("Красивых слов с длиной 3: " + counter3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + counter4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + counter5 + " шт.");
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
