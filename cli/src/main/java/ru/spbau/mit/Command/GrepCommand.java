package ru.spbau.mit.Command;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import ru.spbau.mit.Environment;
import ru.spbau.mit.ExecutionResult;
import ru.spbau.mit.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * class for command grep
 * find line with matching substring
 * flags:
 * -w if match only full word
 * -i NOT register sensitive
 * -A n count of line after matching line
 *
 * take regexp and after that files names
 * ignore input stream if have some files name
 */
public class GrepCommand extends Command {
    private String regex;
    private StringBuilder grepResult;
    private String[] argsToParse;
    private boolean registerSensitive = true;
    private boolean onlyFullWord = false;
    private int cntLine = 1;
    private List<String> nonOptionArg;

    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult result = new ExecutionResult();
        arrayListToArray();
        parseOption();

        regex = nonOptionArg.get(0);
        grepResult = new StringBuilder();

        if (nonOptionArg.size() > 1) {
            for (int i = 1; i < nonOptionArg.size(); ++i) {
                FileInputStream fileInputStream = new FileInputStream(nonOptionArg.get(i));
                findRegexp(fileInputStream);
            }
        } else {
            findRegexp(stdin);
        }

        result.setStdout(Utils.fromStringToInputStream(grepResult.toString()));
        return result;
    }

    private void parseOption() {
        OptionParser parser = new OptionParser("iwA:");
        OptionSet options = parser.parse(argsToParse);

        if (options.has("i")) {
            registerSensitive = false;
        }
        if (options.has("w")) {
            onlyFullWord = true;
        }
        if (options.has("A")) {
            cntLine = Integer.parseInt(String.valueOf(options.valueOf("A")));
        }

        nonOptionArg = new ArrayList<>();
        nonOptionArg.addAll(options.nonOptionArguments().stream().filter(arg -> arg instanceof String).
                map(arg -> (String) arg).collect(Collectors.toList()));
    }

    private void arrayListToArray() {
        argsToParse = new String[argSize()];
        for (int i = 0; i < argSize(); ++i) {
            argsToParse[i] = getArg(i);
        }
    }

    private void findRegexp(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        if (!registerSensitive) {
            regex = regex.toLowerCase();
        }

        String strLine;
        int cntLineToPrint = 0;

        while ((strLine = br.readLine()) != null) {
            String realLine = strLine;
            if (!registerSensitive) {
                strLine = strLine.toLowerCase();
            }

            if (!onlyFullWord) {
                Matcher m = Pattern.compile(regex).matcher(strLine);
                if (m.find()) {
                    cntLineToPrint = cntLine;
                }
            } else {
                Scanner sc = new Scanner(strLine);
                while (sc.hasNext()) {
                    String word = sc.next();

                    Matcher m = Pattern.compile(regex).matcher(word);

                    if (m.find() && m.end() == word.length() && m.start() == 0) {
                        cntLineToPrint = cntLine;
                    }
                }
            }
            if (cntLineToPrint > 0) {
                --cntLineToPrint;
                grepResult.append(realLine);
                grepResult.append("\n");
            }
        }

        br.close();
    }
}
