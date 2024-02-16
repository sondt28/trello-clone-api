package com.son.todolist.todo;

import com.son.todolist.common.exception.NotFoundException;
import com.son.todolist.section.Section;
import com.son.todolist.section.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl {
    private final TodoRepository todoRepository;
    private final SectionRepository sectionRepository;
    private final TodoMapper todoMapper;

    public TodoDto findById(Long id) {
        Todo todo = findTodoById(id);

        return todoMapper.todoToTodoDto(todo);
    }

    public List<TodoDto> findAllBySection(Long sectionId) {
        List<Todo> todos = todoRepository.findBySectionId(sectionId);

        return todoMapper.pageTodoToPageTodoDto(todos);
    }

    public TodoDto save(TodoDto dto) {
        Section section = sectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new NotFoundException("Section not found."));

        Todo todo = todoMapper.dtoToTodo(dto, section);
        Todo savedTodo = todoRepository.save(todo);

        return todoMapper.todoToTodoDto(savedTodo);
    }

    public void update(Long id, TodoDto dto) {
        Todo todo = findTodoById(id);

        todoMapper.updateTodoFromDto(dto, todo);
        todoRepository.save(todo);
    }

    public void delete(Long id) {
        Todo todo = findTodoById(id);
        todoRepository.deleteById(id);
    }

    public void moveOnSection(Long todoId, Long sectionId, TodoOrderDto dto) {
        Todo todo = findTodoByIdAndSectionId(todoId, sectionId);

        int totalTodoOnSection = todoRepository.countTodoBySectionId(sectionId);
        int oldOrder = todo.getOrder();
        int newOrder = dto.getNewOrder();

        if (newOrder > oldOrder && newOrder < totalTodoOnSection)
            todoRepository.decrementAboveToPosition(newOrder, oldOrder, sectionId);
        else if (newOrder < oldOrder && newOrder >= 0)
            todoRepository.incrementBelowToPosition(newOrder, oldOrder, sectionId);
        else
            throw new NotFoundException("Invalid order.");

        todo.setOrder(newOrder);

        todoRepository.save(todo);
    }

    public void moveToSection(Long todoId, Long sectionId, TodoSectionDto dto) {
        Todo todo = findTodoByIdAndSectionId(todoId, sectionId);
        Section newSection = sectionRepository.findById(dto.getNewSectionId())
                .orElseThrow(() -> new NotFoundException("New section not found."));

        Long projectIdOfOldSection = todo.getSection().getProject().getId();
        Long projectIdOfNewSection = newSection.getProject().getId();

        if (!projectIdOfNewSection.equals(projectIdOfOldSection))
            throw new NotFoundException("Invalid section.");

        int totalTodoOfNewSection = todoRepository.countTodoBySectionId(dto.getNewSectionId());
        int oldOrder = todo.getOrder();
        int newOrder = dto.getNewOrder();

        if (newOrder >= 0 && newOrder < totalTodoOfNewSection) {
            todoRepository.incrementOrder(newOrder, dto.getNewSectionId());
            todoRepository.decrementOrder(oldOrder, sectionId);
        }

        todo.setOrder(newOrder);
        todo.setSection(newSection);

        todoRepository.save(todo);
    }

    private Todo findTodoById(Long id) {
        return todoRepository.findByTodoId(id)
                .orElseThrow(() -> new NotFoundException("Todo not found."));
    }

    private Todo findTodoByIdAndSectionId(Long id, Long sectionId) {
        return todoRepository.findByIdAndSectionId(id, sectionId)
                .orElseThrow(() -> new NotFoundException("Todo not found."));
    }
}
