package com.senac.academo.service;

import com.senac.academo.model.entity.Matricula;
import com.senac.academo.model.enums.StatusMatricula;
import com.senac.academo.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    public Matricula criarMatricula(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    public Optional<Matricula> buscarPorId(Integer id) {
        return matriculaRepository.findById(id);
    }

    public List<Matricula> buscarPorAluno(Integer alunoId) {
        return matriculaRepository.findByAlunoId(alunoId);
    }

    public List<Matricula> buscarPorDisciplina(Integer disciplinaId) {
        return matriculaRepository.findByDisciplinaId(disciplinaId);
    }

    public List<Matricula> buscarPorStatus(StatusMatricula status) {
        return matriculaRepository.findByStatus(status);
    }

    public Matricula atualizarMatricula(Integer id, Matricula matriculaAtualizada) {
        return matriculaRepository.findById(id)
                .map(matricula -> {
                    matricula.setAluno(matriculaAtualizada.getAluno());
                    matricula.setDisciplina(matriculaAtualizada.getDisciplina());
                    matricula.setDataMatricula(matriculaAtualizada.getDataMatricula());
                    matricula.setStatus(matriculaAtualizada.getStatus());
                    matricula.setSituacao(matriculaAtualizada.getSituacao());
                    matricula.setMediaFinal(matriculaAtualizada.getMediaFinal());
                    matricula.setCargaHorariaPresente(matriculaAtualizada.getCargaHorariaPresente());
                    return matriculaRepository.save(matricula);
                })
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
    }

    public void deletarMatricula(Integer id) {
        matriculaRepository.deleteById(id);
    }
}