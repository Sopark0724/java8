package chapter2;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Iteration {

    @Test
    public void 기존for문() {
        final List<String> friends = Arrays.asList("sopark", "tkddh", "sharky");

        for (int i = 0; i < friends.size(); i++) {
            System.out.println(friends.get(i));
        }
    }

    @Test
    public void java8API사용(){
        final List<String> friends = Arrays.asList("sopark", "tkddh", "sharky");

        // Iterable 인터페이스에 java8 부터 생성됨
        friends.forEach(new Consumer<String>() {
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

    @Test
    public void 람다사용_파리미터_타임지정(){
        final List<String> friends = Arrays.asList("sopark", "tkddh", "sharky");

        // Iterable 인터페이스에 java8 부터 생성됨
        // 파라미터의 타입을 지정
        friends.forEach((final String name) -> System.out.println(name));
    }

    @Test
    public void 람다사용_파리미터_타입추론(){
        final List<String> friends = Arrays.asList("sopark", "tkddh", "sharky");

        // Iterable 인터페이스에 java8 부터 생성됨
        // 파라미터의 타입을 타입 추론으로 처리.
        // 단, final 에 대한 처리는 별도로 하지 않기 때문에 필요에 따라서 설정 필요
        friends.forEach(name -> System.out.println(name));
    }

    @Test
    public void 람다사용_메소드레퍼런스(){
        final List<String> friends = Arrays.asList("sopark", "tkddh", "sharky");

        // Iterable 인터페이스에 java8 부터 생성됨
        // 메소드 레퍼런스 (forEach 에서의 값과 println 의 파라미터가 같을 경우 사용가능)
        friends.forEach(System.out::println);
    }

    @Test
    public void test1(){
        List<Integer> arrays = Arrays.asList(1,2,3,4);

        arrays.stream()
              .collect(toList());

        arrays.stream()
                .collect(toSet());

    }

    @Test
    public void HighOrderFunction(){
        Arrays.asList(1,2,3);
        final Function<Function<Integer, String>, String> f = g -> g.apply(10);
        System.out.println(f.apply(i -> "#" + i));

        final Function<Integer, Function<Integer, Integer>> f2 = (i) -> ((i2) -> i + i2);
        System.out.println(f2.apply(1).apply(9));
    }
}
