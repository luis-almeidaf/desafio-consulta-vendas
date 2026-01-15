package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SaleReportDTO> getReport(String minDateString, String maxDateString, String name, Pageable pageable) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate maxDate = maxDateString.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
                : LocalDate.parse(maxDateString, format);

        LocalDate minDate = minDateString.isEmpty() ? maxDate.minusYears(1)
                : LocalDate.parse(minDateString, format);

        return repository.getReport(minDate, maxDate, name, pageable);
    }

    public List<SaleSummaryDTO> getSummary(String minDateString, String maxDateString) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate maxDate = maxDateString.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
                : LocalDate.parse(maxDateString, format);

        LocalDate minDate = minDateString.isEmpty() ? maxDate.minusYears(1)
                : LocalDate.parse(minDateString, format);

        return repository.getSalesSummary(minDate, maxDate);
    }
}


