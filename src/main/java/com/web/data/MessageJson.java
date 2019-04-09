package com.web.data;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Setter
@Getter
@NoArgsConstructor
public class MessageJson {

	private Long senderId;
    private String content;
    private String sender;
    private String userPicName;

}
