package com.doteyplay.game.config.template;

import com.doteyplay.game.util.excel.ExcelRowBinding;
import com.doteyplay.game.util.excel.TemplateService;

@ExcelRowBinding
public class SkillDataObject extends SkillDataTemplate {

	protected BuffDataTemplate buffDataObject;

	public BuffDataTemplate getBuffDataObject() {
		return buffDataObject;
	}

	@Override
	public void patchUp() throws Exception {
		super.patchUp();

		buffDataObject = TemplateService.getInstance().get(this.buffId,
				BuffDataTemplate.class);

	}

}
