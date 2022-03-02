Content-Type: text/html; charset="utf-8"
    <html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
      xmlns:w="urn:schemas-microsoft-com:office:word" xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
      xmlns="http://www.w3.org/TR/REC-html40">
    <head>
    <!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/>
    <w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/>
    <w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val="Cambria Math"/><m:brkBin m:val="before"/><m:brkBinSub m:val="--"/><m:smallFrac m:val="off"/>
    <m:dispDef/><m:lMargin m:val="0"/> <m:rMargin m:val="0"/><m:defJc m:val="centerGroup"/><m:wrapIndent m:val="1440"/><m:intLim m:val="subSup"/><m:naryLim m:val="undOvr"/></m:mathPr></w:WordDocument></xml><![endif]-->
    <style>
      *{font-family:'宋体';font-size:16px}
        table{width:90%;margin-left:20px;margin-bottom:13px;border-collapse:collapse}
        table,th,td{border:1px solid #000}
        td{padding:4px 8px 3px;min-height:30px;word-break:break-all}
        .wrap{width:34%;text-align:center;margin:0 auto}
        .file-title{font-size:16pt;font-weight:700;margin-bottom:10px}
        .file-time{font-size:10.5pt;margin-bottom:11px}
        .contain-title{font-size:14pt;font-weight:700;text-align:left;margin-top:20pt;margin-bottom:10pt}
        .list-title{font-size:16px;font-weight:700;text-align:left;margin-top:10pt;margin-bottom:10pt}
        .table-title{font-size:16px;font-weight:700;text-align:center;margin-top:10pt;margin-bottom:10pt}
        .main-left{text-align:left}
        .main-center{text-align:center}
        .main-totol{text-align:center;font-weight:700}
        .direction{text-align:left;font-size:10pt}
        .table-ftmin{font-size:7.5pt}
    </style>
    </head>
    <body>
      <div class="wrap">
            <div class="file-title">无人机智能巡检报告</div>
  <div class="file-time">报告生成时间：${date}</div>
   <div class="contain-title">一、基础信息</div>
   <table>
      <tr>
        <td class="main-center" width="25%">巡检区域</td>
        <td class="main-left" colspan="3">${inspectionArea}米</td>
      </tr>
      <tr>
        <td class="main-center">巡检计划</td>
        <td class="main-left" colspan="3">${inspectionPlan}</td>
      </tr>
      <tr>
        <td class="main-center">巡检时间</td>
        <td class="main-left" colspan="3">
          ${inspectionBeginTime} 至
          ${inspectionEndTime}</td>
      </tr>
      <tr>
        <td class="main-center">巡检进度</td>
        <td class="main-center">${inspectionProcess}</td>
        <td class="main-center" width="25%">巡检班组</td>
        <td class="main-center">${inspectionGroup}</td>
      </tr>
    </table>
            <div class="contain-title">二、巡检诊断信息</div>
            <div class="list-title">1、诊断明细</div>
              <div class="table-title">表一：巡检诊断总览表</div>
  <table>
    <tr>
      <td class="main-center" width="25%">拍摄照片总数</td>
      <td class="main-center" width="25%">${picTotal}张</td>
      <td class="main-center" width="25%">缺陷照片总数</td>
      <td class="main-center" width="25%">${defectPicTotal}张</td>
    </tr>
    <tr>
      <td class="main-center">可见光照片数</td>
      <td class="main-center">${visiblePicTotal}张</td>
      <td class="main-center">红外照片数</td><td class="main-center">${infraredPicTotal}张</td>
    </tr>
    <tr>
      <td class="main-center">可见光缺陷数</td><td class="main-center">${visibleDefectPicTotal}个</td>
      <td class="main-center">红外缺陷数</td><td class="main-center">${infraredDefectPicTotal}个</td>
    </tr>
  </table>
              <div class="table-title">表二：缺陷统计表</div>
      <table>
        <tr>
          <th class="main-center" width="22%">各缺陷类型</th>
          <th class="main-center" width="17%">缺陷总数</th>
          <th class="main-center" width="17%">一般缺陷数</th>
          <th class="main-center" width="17%">严重缺陷数</th>
          <th class="main-center" width="17%">危急缺陷数</th>
        </tr>
        
        <tr>
          <td class="main-totol">缺陷总计</td>
          <td class="main-totol">${totalNum}</td>
          <td class="main-totol">${normalNum}</td>
          <td class="main-totol">${seriousNum}</td>
          <td class="main-totol">${dangerNum}</td>
        </tr>
      </table>
            <div class="list-title" style="margin-top: 20pt;">2、缺陷位置信息表</div>
            <p class="direction">&nbsp;&nbsp;无人机巡检过程中，拍摄的每张照片都有其对应的GPS坐标，
              通过无人机智能巡检系统识别出缺陷位置信息列表，帮助运维人员更准确、快捷地定位缺陷位置。</p>
            <div class="table-title">缺陷位置信息表</div>
            <table>
        <tr>
          <td class="main-center" width="8%">照片序号</td>
          <td class="main-center" width="20%">
            区域名称
          </td>
          <td class="main-center" width="10%">拍摄经度</td>
          <td class="main-center" width="10%">拍摄纬度</td>
          <td class="main-center" width="8%">缺陷个数</td>
          <td class="main-center" width="8%">一般缺陷</td>
          <td class="main-center" width="8%">严重缺陷</td>
          <td class="main-center" width="8%">危急缺陷</td>
        </tr>
		<#list list1 as list1>
        <tr>
      <td class="main-center"> ${list1.picId} </td><td class="main-center"> ${list1.areaName} </td><td class="main-center"> ${list1.longitude} </td><td class="main-center"> ${list1.latitude} </td><td class="main-center"> ${list1.defectNum} </td><td class="main-center"> ${list1.commonDefectNum} </td><td class="main-center"> ${list1.seriousDefectNum} </td><td class="main-center"> ${list1.criticalDefectNum} </td>
    </tr>
	</#list>

      </table>
          </div>
    </body>
  </html>