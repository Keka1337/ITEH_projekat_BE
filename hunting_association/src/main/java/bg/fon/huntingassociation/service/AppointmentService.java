package bg.fon.huntingassociation.service;

import bg.fon.huntingassociation.domain.Appointment;
import bg.fon.huntingassociation.domain.Team;
import bg.fon.huntingassociation.domain.Venison;
import bg.fon.huntingassociation.domain.dtos.AppointmentDto;
import bg.fon.huntingassociation.mappers.AppointmentMapper;
import bg.fon.huntingassociation.repository.AppointmentRepository;
import bg.fon.huntingassociation.repository.TeamRepository;
import bg.fon.huntingassociation.repository.VenisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TeamRepository teamRepository;
    private final VenisonRepository venisonRepository;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, TeamRepository teamRepository, VenisonRepository venisonRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.teamRepository = teamRepository;
        this.venisonRepository = venisonRepository;
        this.appointmentMapper = appointmentMapper;
    }

    public AppointmentDto createAppointment(Appointment appointment) {
        Appointment created = appointmentRepository.save(appointment);
        Venison venison = venisonRepository.findById(created.getVenison().getId()).get();
        Team team = teamRepository.findById(created.getTeam().getId()).get();
        created.setVenison(venison);
        created.setTeam(team);
        return appointmentMapper.entityToDto(created);
    }

    public List<AppointmentDto> findAllAppointments() {
        return this.appointmentRepository.findAll()
                .stream()
                .map(appointment -> appointmentMapper.entityToDto(appointment)).collect(Collectors.toList());
    }

    public AppointmentDto findAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        return appointmentMapper.entityToDto(appointment);
    }

    public void deleteAppoitment(Long id) {
        this.appointmentRepository.deleteAppointmentById(id);
    }

    public  HashMap<String, Object> findAllPageable(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable;
        if("date".equals(sortBy))
            pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());
        else
            pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).ascending());

        Page page = this.appointmentRepository.findAll(pageable);

        List<Appointment> appointments = page.getContent();
        List<AppointmentDto> dtos = appointments.stream().map(appointment -> appointmentMapper.entityToDto(appointment)).collect(Collectors.toList());
        Long total = page.getTotalElements();

        HashMap<String, Object> map = new HashMap();
        map.put("total", total);
        map.put("content",dtos);

        return map;
    }
}
