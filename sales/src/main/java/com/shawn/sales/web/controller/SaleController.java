package com.shawn.sales.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shawn.sales.business.RepairService;
import com.shawn.sales.business.SaleService;
import com.shawn.sales.business.VisitService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.RepairRecord;
import com.shawn.sales.business.model.SaleRecord;
import com.shawn.sales.business.model.VisitRecord;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;

@Controller
@RequestMapping(value = "/sale")
public class SaleController {

	@Autowired
	private SaleService saleService;
	@Autowired
	private VisitService visitService;
	@Autowired
	private RepairService repairService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/create")
	public @ResponseBody
	ResultDto<SaleRecord> create(@RequestBody SaleRecord sale) {
		ResultDto<SaleRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = saleService.create(sale);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/list")
	public @ResponseBody
	ResultDto<Page<SaleRecord>> getList(HttpServletRequest request, @RequestParam(value = "userId", required = false) Long userId, @RequestParam("page") Integer page, @RequestParam("count") Integer count) {
		ResultDto<Page<SaleRecord>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {

			ret = saleService.getList(null, page, count);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/search")
	public @ResponseBody
	ResultDto<Page<SaleRecord>> search(HttpServletRequest request, @RequestParam(value = "keywords",required=false) String keywords, @RequestParam(value="dateWords",required=false) String dateWords,@RequestParam(value = "userId", required = false) Long userId, @RequestParam("page") Integer page, @RequestParam("count") Integer count) {
		ResultDto<Page<SaleRecord>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = saleService.search(null, page, count, keywords,dateWords);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/update")
	public @ResponseBody
	ResultDto<SaleRecord> update(@RequestBody SaleRecord sale) {
		ResultDto<SaleRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = saleService.update(sale);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/delete")
	public @ResponseBody
	ResultDto<SaleRecord> delete(@RequestParam(value = "id") Long id) {
		ResultDto<SaleRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = saleService.delete(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/get")
	public @ResponseBody
	ResultDto<SaleRecord> get(@RequestParam(value = "id") Long id) {
		ResultDto<SaleRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = saleService.get(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/process")
	public @ResponseBody
	ResultDto<SaleRecord> process(@RequestParam(value = "id") Long id) {
		ResultDto<SaleRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = saleService.process(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/visit/list")
	public @ResponseBody
	ResultDto<Page<VisitRecord>> getVisitList(HttpServletRequest request, @RequestParam(value = "saleId", required = false) Long saleId, @RequestParam("page") Integer page, @RequestParam("count") Integer count) {
		ResultDto<Page<VisitRecord>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = visitService.getList(saleId, page, count);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/repair/list")
	public @ResponseBody
	ResultDto<Page<RepairRecord>> getRepairList(HttpServletRequest request, @RequestParam(value = "saleId", required = false) Long saleId, @RequestParam("page") Integer page, @RequestParam("count") Integer count) {
		ResultDto<Page<RepairRecord>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = repairService.getList(saleId, page, count);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/visit/create")
	public @ResponseBody
	ResultDto<VisitRecord> createVisit(@RequestBody VisitRecord visit) {
		ResultDto<VisitRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = visitService.create(visit);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/repair/create")
	public @ResponseBody
	ResultDto<RepairRecord> createVisit(@RequestBody RepairRecord repair) {
		ResultDto<RepairRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = repairService.create(repair);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/visit/delete")
	public @ResponseBody
	ResultDto<VisitRecord> deleteVisit(@RequestParam(value = "id") Long id) {
		ResultDto<VisitRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = visitService.delete(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/repair/delete")
	public @ResponseBody
	ResultDto<RepairRecord> deleteRepair(@RequestParam(value = "id") Long id) {
		ResultDto<RepairRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = repairService.delete(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
}
