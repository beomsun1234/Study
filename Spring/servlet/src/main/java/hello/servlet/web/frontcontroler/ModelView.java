package hello.servlet.web.frontcontroler;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


public class ModelView {
    @Getter @Setter
    private String viewName;
    @Getter @Setter
    private Map<String, Object> model = new HashMap<>();


    public ModelView(String viewName){
        this.viewName = viewName;
    }
}
