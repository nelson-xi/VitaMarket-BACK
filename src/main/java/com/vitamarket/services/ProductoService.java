package com.vitamarket.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vitamarket.models.Producto;
import com.vitamarket.models.utils.PagingHeaders;
import com.vitamarket.models.utils.PagingResponse;
import com.vitamarket.repositories.ProductoRepo;

@Service
@Transactional
public class ProductoService {

	@Autowired
	ProductoRepo repository;

	/**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
	public List<Producto> get(Specification<Producto> spec, Sort sort) {
        return repository.findAll(spec, sort);
    }
	
	public Optional<Producto> getOne(Long id) {
		return repository.findById(id);
	}
	
	public Producto save(Producto producto) {
		return repository.save(producto);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	/**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Producto> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<Producto> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }

    /**
     * get elements using Criteria.
     *
     * @param spec     *
     * @param pageable pagination data
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Producto> spec, Pageable pageable) {
        Page<Producto> page = repository.findAll(spec, pageable);
        List<Producto> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }
	
}
