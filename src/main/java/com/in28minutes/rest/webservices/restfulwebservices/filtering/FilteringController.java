package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

// Make sure to use "RestController" when dealing with RESTful interfaces... duh
@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public SomeBean retrieveSomeBean() {
        return new SomeBean("value 1", "value 2", "value 3");
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> retrieveListOfSomeBeans() {
        return Arrays.asList(new SomeBean("value 1", "value 2", "value 3"),
                new SomeBean("value12", "value13", "value14"));
    }

    @GetMapping("/filtering-properties-list")
    public List<SomeOtherBean> retrieveListOfSomeOtherBeans() {
        return Arrays.asList(new SomeOtherBean("value 1", "value 2", "value 3"),
                new SomeOtherBean("value12", "value13", "value14"));
    }

    // only show field 1 and field 2 of the SomeDynamicField  filter
    @GetMapping("/filtering-dynamic-12")
    public MappingJacksonValue retrieveDynamicSomeBean() {

        // create SomeBean with passed values
        SomeDynamicBean someDynamicBean = new SomeDynamicBean("value 1", "value 2", "value 3");

        // create a simple property filter that will only show SomeBean fields "s1" and "s2"
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("s1", "s2");

        // add the filter(s) to the filter provider
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeDynamicBeanFilter", filter);

        // Allow the someBean object to accept filters on the "someBean" object
        MappingJacksonValue mapping = new MappingJacksonValue(someDynamicBean);

        // apply the filters to the mapped object (in this case, "someBean" from the previous line)
        mapping.setFilters(filters);

        // return the now-filtered "someBean" object
        return mapping;
    }

    // only show field 1 and field 2 of the SomeDynamicField  filter
    @GetMapping("/filtering-dynamic-13")
    public MappingJacksonValue retrieveAnotherDynamicSomeBean() {

        // create SomeBean with passed values
        SomeDynamicBean someDynamicBean = new SomeDynamicBean("value 1", "value 2", "value 3");

        // create a simple property filter that will only show SomeBean fields "s1" and "s2"
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("s1", "s3");

        // add the filter(s) to the filter provider
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeDynamicBeanFilter", filter);

        // Allow the someBean object to accept filters on the "someBean" object
        MappingJacksonValue mapping = new MappingJacksonValue(someDynamicBean);

        // apply the filters to the mapped object (in this case, "someBean" from the previous line)
        mapping.setFilters(filters);

        // return the now-filtered "someBean" object
        return mapping;
    }


}
