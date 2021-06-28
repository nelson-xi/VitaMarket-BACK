package com.vitamarket.security.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.vitamarket.security.enums.RolNombre;
import lombok.*;

@Entity
@Table(name = "rol")
@Getter @Setter @NoArgsConstructor
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name="rol_nombre")
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;
	
	public Rol(@NotNull RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
	
}
