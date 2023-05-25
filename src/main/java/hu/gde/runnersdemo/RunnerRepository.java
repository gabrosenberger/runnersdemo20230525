package hu.gde.runnersdemo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunnerRepository extends JpaRepository<RunnerEntity,Long > {
    List<RunnerEntity> findByOrderByHeightDesc();
}
