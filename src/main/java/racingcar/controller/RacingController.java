package racingcar.controller;

import java.util.List;
import java.util.stream.Collectors;
import racingcar.model.Car;
import racingcar.model.CarName;
import racingcar.model.Cars;
import racingcar.model.RacingGame;
import racingcar.model.RandomNumberGenerator;
import racingcar.model.TotalResult;
import racingcar.model.TryCount;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class RacingController {

    private final InputView inputView;
    private final OutputView outputView;

    public RacingController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Cars cars = createCars();
        RacingGame racingGame = createRacingGame(cars);

        inputView.finishReadingInput();

        TotalResult totalResult = racingGame.run();
        printResult(totalResult);
    }

    private Cars createCars() {
        List<String> carNames = inputView.readCarNames();
        return carNames.stream()
                .map(CarName::new)
                .map(Car::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Cars::new));
    }

    private RacingGame createRacingGame(Cars cars) {
        int tryCount = inputView.readTryCount();
        return new RacingGame(new TryCount(tryCount), cars, new RandomNumberGenerator());
    }

    private void printResult(TotalResult totalResult) {
        outputView.printResult(totalResult);
        outputView.printWinnerInfo(totalResult.getWinner());
    }
}
