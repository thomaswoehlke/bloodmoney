package org.woehlke.bloodmoney.frontend.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Stack;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession implements Serializable {

    private static final long serialVersionUID = -9184465632849128728L;

    @NotNull
    private Stack<FlashMessage> stack = new Stack<>();

    @NotNull
    private Boolean devTesting;

}
