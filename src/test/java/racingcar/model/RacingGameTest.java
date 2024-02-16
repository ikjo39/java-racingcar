package racingcar.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RacingGameTest {

    @DisplayName("이동 횟수가 1 미만 숫자인 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-100000"})
    void exceptionInvalidCountInput(int given) {
        //given
        Cars cars = createCars();

        //then
        assertThatThrownBy(() -> new RacingGame(given, cars, new RandomNumberGenerator()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("TryCount가 ")
                .hasMessageContaining("보다 작은 숫자가 입력되었습니다. 입력값 : ");
    }

    @DisplayName("게임 횟수 만큼 게임을 실행한다.")
    @Test
    void runGameByCount() {
        //given
        int count = 3;
        Cars cars = createCars();
        RacingGame racingGame = new RacingGame(count, cars, new RandomNumberGenerator());

        //when
        TotalResult totalResult = racingGame.run();

        //then
        assertThat(totalResult.getTotalResult()).hasSize(count);
    }

    private static Cars createCars() {
        List<String> carNames = List.of("a", "b", "c");
        List<Car> carList = carNames.stream()
                .map(Car::new)
                .toList();
        return new Cars(carList);
    }
}
