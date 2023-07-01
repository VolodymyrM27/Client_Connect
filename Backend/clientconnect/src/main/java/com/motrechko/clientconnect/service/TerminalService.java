package com.motrechko.clientconnect.service;

import com.motrechko.clientconnect.dto.TerminalDto;
import com.motrechko.clientconnect.exception.TerminalNotFoundException;
import com.motrechko.clientconnect.mapper.TerminalMapper;
import com.motrechko.clientconnect.model.Terminal;
import com.motrechko.clientconnect.repository.TerminalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TerminalService {

    private final TerminalRepository repository;
    private final TerminalMapper terminalMapper;
    public List<TerminalDto> getAllBusinessTermainals(Long businessId){
        return terminalMapper.toDto(repository.findByBusiness_Id(businessId));
    };

    public TerminalDto create(TerminalDto terminalDto) {
        Terminal terminal = terminalMapper.toEntity(terminalDto);
        terminal.setUuid(UUID.randomUUID().toString());
        return terminalMapper.toDto(repository.save(terminal));
    }

    public Terminal getTerminalByTerminalUUID(String terminalUUID){
        return repository.findByUuid(terminalUUID)
                .orElseThrow(() -> new TerminalNotFoundException(terminalUUID));
    }

    @Transactional
    public void deleteTerminal(Long businessId, Long termainalId) {
         repository.deleteByBusinessIdAndTerminalId(businessId,termainalId);
    }


}
