package com.attilapalf.exceptional.repositories.devices;

import com.attilapalf.exceptional.entities.DevicesEntity;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by Attila on 2015-06-11.
 */
public interface DeviceCrud extends CrudRepository<DevicesEntity, String> {
}
