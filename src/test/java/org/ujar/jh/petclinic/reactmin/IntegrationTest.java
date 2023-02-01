package org.ujar.jh.petclinic.reactmin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.ujar.jh.petclinic.reactmin.PetclinicApp;
import org.ujar.jh.petclinic.reactmin.config.AsyncSyncConfiguration;
import org.ujar.jh.petclinic.reactmin.config.EmbeddedSQL;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { PetclinicApp.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
