package todo.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import todo.dto.TodoDto;
import todo.entity.Todo;
import todo.exception.ResourceNotFoundException;
import todo.repository.TodoRepository;
import todo.service.TodoService;

import java.util.List;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with id: "+id));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map(todo->modelMapper.map(todo, TodoDto.class)).toList();
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        todoRepository.deleteById(todo.getId());
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        todo.setCompleted(Boolean.TRUE);
        todoRepository.save(todo);
        TodoDto newTodoDto = modelMapper.map(todo, TodoDto.class);
        return newTodoDto;
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        todo.setCompleted(Boolean.FALSE);
        todoRepository.save(todo);
        TodoDto newTodoDto = modelMapper.map(todo, TodoDto.class);
        return newTodoDto;
    }
}