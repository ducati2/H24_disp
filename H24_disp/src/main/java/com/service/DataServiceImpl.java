package com.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.entity.SearcherInfo;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.repository.DataRepository;
import com.repository.RelationRepository;

@Service
public class DataServiceImpl implements DataService {

	@Resource
	private DataRepository dataRepository;
	@Resource
	private RelationRepository relationRepository;

	// date+1
	public static String dateFormatDay(String date) {
		// "2011-10-08"
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(dateFormat.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, 1);
		return dateFormat.format(cal.getTime());
	}

	// week+1
	public static String dateFormatWeek(String date) {
		// "2017-49"
		if (Integer.parseInt(date.substring(5)) > 9
				&& Integer.parseInt(date.substring(5)) < 52) {
			return date.substring(0, 5)
					+ String.valueOf(Integer.parseInt(date.substring(5)) + 1);
		} else if (Integer.parseInt(date.substring(5)) == 52) {
			return Integer.parseInt(date.substring(0, 4)) + 1 + "-01";
		} else
			return date.substring(0, 6)
					+ String.valueOf(Integer.parseInt(date.substring(6)) + 1);
	}

	// month+1
	public static String dateFormatMonth(String date) {
		// 2011-10
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(dateFormat.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, 1);
		return dateFormat.format(cal.getTime());
	}
	
	public static String dateFormatAddZero(int date) {
		return date > 9 ? "" + date : "0" + date;
	}
	
	public static String dateFormat(String date, boolean flag) {
		String[] m = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		List<String> months = Arrays.asList(m);		
		if(date == null || date.replaceAll(" ", "").equals("")){
			if(flag){
				return "0000-00-00";
			}else return "9999-12-30";
		}else{
			String[] datas = date.split(" ");
			String month = dateFormatAddZero(months.indexOf(datas[0])+1);
			String day = dateFormatAddZero(Integer.parseInt(datas[1].split(",")[0]));
			String year = datas[2];
			String resultDate = year + "-" + month + "-" + day;
			if(flag){
				return resultDate;
			}else return dateFormatDay(resultDate);
		}
	}
	
	
	public Option[] getOptionsDataByConditions(String StartDate, String EndDate){
		Option[] options = new Option[6];
		StartDate = dateFormat(StartDate, true);
		EndDate = dateFormat(EndDate, false);
        System.out.println("DataServiceImpl(StartDate):"+StartDate);
		System.out.println("DataServiceImpl(EndDate):"+EndDate);
		//1
		Option option = new Option();
		option.tooltip().show(true).formatter("{a} <br/>{b} : {c} ({d}%)").trigger(Trigger.item);
		option.title().text("Percentage of Helpful Occurs").x(X.center);
		option.legend().orient(Orient.vertical).data("Helpful", "Unhelpful")
				.left(X.left);
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.calculable(true);
		Pie pie = new Pie("1");
		pie.name("Percentage:").type(SeriesType.pie);
		pie.data(new PieData("Helpful", relationRepository.helpfulCountByConditions(StartDate,EndDate)));
		pie.data(new PieData("Unhelpful", dataRepository.totalSearchCountByConditions(StartDate,EndDate)-relationRepository.helpfulCountByConditions(StartDate,EndDate)));
		pie.center("50%", "50%").radius("35%","50%");
		option.series(pie);
		options[0] = option;
		
		//2
		option = new Option();
		option.tooltip().show(true).formatter("{a} <br/>{b} : {c} ({d}%)");
		option.title().text("Percentage of Custom Search")
				.x(X.center);
		option.legend().orient(Orient.vertical).data("Edited", "Unedited")
				.left(X.left);
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.calculable(true);
		pie = new Pie("2");
		pie.name("Percentage:").type(SeriesType.pie);
		pie.data(new PieData("Edited", dataRepository.editCountByConditions(StartDate,EndDate)));
		pie.data(new PieData("Unedited", dataRepository.unEditCountByConditions(StartDate,EndDate)));
		pie.center("50%", "50%").radius("35%","50%");
		option.series(pie);
		options[1] = option;
		
		//3.1
		option = new Option();
		option.title().text("Search Counts(Daily)");
        option.tooltip().trigger(Trigger.axis);
        option.legend("day");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        CategoryAxis categoryAxis = new CategoryAxis();
        List<String> date = dataRepository.getDayByConditions(StartDate,EndDate);
        List<Long> count = dataRepository.getDayCountByConditions(StartDate,EndDate);
		Bar bar = new Bar("Search Counts");
		if (date.size() > 0) {
			String tempDate = "";
			for (int i = 0; i < date.size(); i++) {
				categoryAxis.data(date.get(i));
				bar.data(count.get(i));
				tempDate = date.get(i);
				while (i < date.size() - 1 && !date.get(i + 1).equals((dateFormatDay(tempDate)))) {
					tempDate = dateFormatDay(tempDate);
					categoryAxis.data(tempDate);
					bar.data((long)0);
				}
			}
		}
		bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
        option.xAxis(categoryAxis);
        option.yAxis(new ValueAxis());
        option.color("#33CAFF");
        option.series(bar);
        options[3] = option;
        
        //3.2
        option = new Option();
		option.title().text("Search Counts(Weekly)");
        option.tooltip().trigger(Trigger.axis);
        option.legend("week");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        categoryAxis = new CategoryAxis();
        date = dataRepository.getWeekByConditions(StartDate,EndDate);
        count = dataRepository.getWeekCountByConditions(StartDate,EndDate);
		bar = new Bar("Search Counts");
		if (date.size() > 0) {
			String tempDate = "";
			for (int i = 0; i < date.size(); i++) {
				categoryAxis.data(date.get(i));
				bar.data(count.get(i));
				tempDate = date.get(i);
				while (i < date.size() - 1 && !date.get(i + 1).equals((dateFormatWeek(tempDate)))) {
					tempDate = dateFormatWeek(tempDate);
					categoryAxis.data(tempDate);
					bar.data((long)0);
				}
			}
		}
		bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
        option.xAxis(categoryAxis);
        option.yAxis(new ValueAxis());
        option.color("#33A5FF");
        option.series(bar);
        options[4] = option;
        
        //3.3
        option = new Option();
		option.title().text("Search Counts(Monthly)");
        option.tooltip().trigger(Trigger.axis);
        option.legend("month");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        categoryAxis = new CategoryAxis();
        date = dataRepository.getMonthByConditions(StartDate,EndDate);
        count = dataRepository.getMonthCountByConditions(StartDate,EndDate);
        bar = new Bar("Search Counts");
		if (date.size() > 0) {
			String tempDate = "";
			for (int i = 0; i < date.size(); i++) {
				categoryAxis.data(date.get(i));
				bar.data(count.get(i));
				tempDate = date.get(i);
				while (i < date.size() - 1 && !date.get(i + 1).equals((dateFormatMonth(tempDate)))) {
					tempDate = dateFormatMonth(tempDate);
					categoryAxis.data(tempDate);
					bar.data((long)0);
				}
			}
		}
		bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
        option.xAxis(categoryAxis);
        option.yAxis(new ValueAxis());
        option.color("#3371FF");
        option.series(bar);
        options[5] = option;
		return options;
	}
	

	public List<SearcherInfo> getSearcherInfo(String StartDate, String EndDate){
		StartDate = dateFormat(StartDate, true);
		EndDate = dateFormat(EndDate, false);
		List<SearcherInfo> searcherInfoList = new ArrayList<SearcherInfo>();
		List<String> seacherName = dataRepository.getSearcherNameByConditions(StartDate,EndDate);
		List<BigInteger> searcherCount = dataRepository.getSearcherCountByConditions(StartDate,EndDate);
		for(int i = 0 ; i < seacherName.size() ; i++){
			searcherInfoList.add(new SearcherInfo(seacherName.get(i), (long) searcherCount.get(i).intValue()));
		}
		System.out.println(searcherInfoList.size());
		return searcherInfoList;
	}
}
