/*
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.model.workflow.nodedata;

import com.alibaba.cloud.ai.model.Variable;
import com.alibaba.cloud.ai.model.VariableSelector;
import com.alibaba.cloud.ai.model.workflow.NodeData;

import java.util.List;

public class EndNodeData extends NodeData {

	public String getOutputKey() {
		return outputKey;
	}

	public EndNodeData setOutputKey(String outputKey) {
		this.outputKey = outputKey;
		return this;
	}

	private String outputKey;

	public EndNodeData() {
	}

	public EndNodeData(List<VariableSelector> inputs, List<Variable> outputs) {
		super(inputs, outputs);
	}

}
