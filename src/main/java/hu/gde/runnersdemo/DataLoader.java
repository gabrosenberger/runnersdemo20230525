package hu.gde.runnersdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    private final RunnerRepository runnerRepository;

    @Autowired
    public DataLoader(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
    }

    @Override
    public void run(String... args) {
        RunnerEntity runnerEntity = new RunnerEntity();
        runnerEntity.setRunnerName("Tomi");
        runnerEntity.setAveragePace(310);
        runnerEntity.setHeight(181);

        LapTimeEntity laptime1 = new LapTimeEntity();
        laptime1.setLapNumber(1);
        laptime1.setTimeSeconds(120);
        laptime1.setRunner(runnerEntity);

        LapTimeEntity laptime2 = new LapTimeEntity();
        laptime2.setLapNumber(2);
        laptime2.setTimeSeconds(110);
        laptime2.setRunner(runnerEntity);

        SponsorEntity sponsor1 = new SponsorEntity();
        sponsor1.setSponsorName("Béla");
        runnerEntity.setSponsor(sponsor1);

        runnerEntity.getLaptimes().add(laptime1);
        runnerEntity.getLaptimes().add(laptime2);

        runnerRepository.save(runnerEntity);

        RunnerEntity runnerEntity2 = new RunnerEntity();
        runnerEntity2.setRunnerName("Zsuzsi");
        runnerEntity2.setAveragePace(290);
        runnerEntity2.setHeight(165);


        LapTimeEntity laptime3 = new LapTimeEntity();
        laptime3.setLapNumber(1);
        laptime3.setTimeSeconds(95);
        laptime3.setRunner(runnerEntity2);

        LapTimeEntity laptime4 = new LapTimeEntity();
        laptime4.setLapNumber(2);
        laptime4.setTimeSeconds(100);
        laptime4.setRunner(runnerEntity2);

        SponsorEntity sponsor2 = new SponsorEntity();
        sponsor2.setSponsorName("Ödön");
        runnerEntity2.setSponsor(sponsor2);

        runnerEntity2.getLaptimes().add(laptime3);
        runnerEntity2.getLaptimes().add(laptime4);

        runnerRepository.save(runnerEntity2);

        RunnerEntity runnerEntity3 = new RunnerEntity();
        runnerEntity3.setRunnerName("Gábor");
        runnerEntity3.setAveragePace(250);
        runnerEntity3.setHeight(200);

        LapTimeEntity laptime5 = new LapTimeEntity();
        laptime5.setLapNumber(3);
        laptime5.setTimeSeconds(150);
        laptime5.setRunner(runnerEntity3);

        SponsorEntity sponsor3 = new SponsorEntity();
        sponsor3.setSponsorName("Ubul");
        runnerEntity3.setSponsor(sponsor3);

        runnerEntity3.getLaptimes().add(laptime5);

        runnerRepository.save(runnerEntity3);

    }
}

