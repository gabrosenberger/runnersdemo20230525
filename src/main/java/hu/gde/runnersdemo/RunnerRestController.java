package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runner")
public class RunnerRestController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;

    private SponsorRepository sponsorRepository;


    @Autowired
    public RunnerRestController(RunnerRepository runnerRepository, LapTimeRepository lapTimeRepository, SponsorRepository sponsorRepository) {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
        this.sponsorRepository = sponsorRepository;
    }

    @GetMapping("/{id}")
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            double averageLaptime = (double) totalTime / laptimes.size();
            return averageLaptime;
        } else {
            return -1.0;
        }
    }

    @GetMapping("/tallestrunnername")
    public String getTallestRunnerName() {
        List<RunnerEntity> runners = runnerRepository.findByOrderByHeightDesc();
        if (runners.size() != 0) {
            return runners.get(0).getRunnerName();
        } else {
            return "";
        }
    }

    @GetMapping("/averageheight")
    public double getAverageHeight() {
        List<RunnerEntity> runners = runnerRepository.findAll();
        if (runners.size() == 0){
            double v = 0;
            return v;
        }
        var totalHeight = 0;
        for (RunnerEntity runner : runners) {

            totalHeight += runner.getHeight();
        }
        double averageHeight = (double) totalHeight / runners.size();
        return averageHeight;
    }

    @GetMapping("")
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @PostMapping("/{id}/addlaptime")
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }
    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }

    @PostMapping("/{id}/setsponsor")
    public ResponseEntity setSponsor(@PathVariable Long id, @RequestBody SponsorRequest sponsorRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            SponsorEntity sponsorEntity = new SponsorEntity();
            sponsorEntity.setSponsorName(sponsorRequest.getSponsorName());
            runner.setSponsor(sponsorEntity);
            runnerRepository.save(runner);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    public static class SponsorRequest {
        private String sponsorName;

        public String getSponsorName() {
            return sponsorName;
        }

        public void setSponsorName(String sponsorName) {
            this.sponsorName = sponsorName;
        }
    }
}
