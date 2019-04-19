package com.demo.rep.data.extractor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.demo.rep.common.SpecialCharacters;

public class DefaultCompanyAliasGenerator implements CompanyAliasGenerator {

	private static final String[] COMPANY_EXTENSIONS = { "A.C.", " A.S.", " A.S.", " A.S.", " A/S", " AB",
			" ACE", " AD", " AE", " AG", " AL", " ANS", " AS", " ASA", " AVV", " AmbA", " ApS", " ApS & Co. K/S",
			" Apb", " B.V.", " BVBA", " Bpk", " Bt", " C.V.", " CA", " CVA", " CVoA", " Corp.", " Co", " Co.",
			" Co Ltd", " Co. Ltd", " Co Inc", " Corporation", " DA", " EE", " EEG", " EIRL", " ELP", " EOOD", " EPE",
			" EURL", " GCV", " GIE", " GbR", " GesmbH", " GmbH", " GmbH & Co. KG", " HB", " I/S", " IBC", " Inc",
			" Inc.", " K/S", " KA/S", " KD", " KDA", " KG", " KGaA", " KK", " KS", " Kb", " Kft", " Kkt", " Kol. SrK",
			" Kom. SrK", " Kv", " Ky", " LDC", " LLC", " LLP", " Lda", " Limited", " Ltd.", " Ltda", " Ltée.", " N.A.",
			" NT", " NV", " OE", " OHG", " OOD", " OYJ", " Oy", " OÜ", " P/L", " PC Ltd", " PLC", " PMA", " PMDN",
			" PT", " PrC", " Prp. Ltd.", " Pty.", " Pvt Ltd", " Pvt. Ltd", " Private Limited", " RAS", " Rt",
			" S. de R.L.", " S. en C.", " S. en N.C.", " S.A.", " S.A.I.C.A.", " S.C.", " S.C.S.", " S/A", " SA",
			" SA de CV", " SAFI", " SAS", " SApA", " SC", " SCA", " SCP", " SCS", " SENC", " SGPS", " SK", " SNC",
			" SOPARFI", " SPRL", " Sarl", " Sdn Bhd", " Sp. z.o.o.", " SpA", " Srl", " TLS", " VEB", " VOF", " d.d.",
			" d.n.o.", " d.o.o.", " d/b/a", " e.V.", " hf", " j.t.d.", " k.d.", " k.d.d.", " k.s.", " sa", " sp",
			" spol s.r.o.", " td", " v.o.s." };
	
	private static final String[] FORMER_NAME_TEXTS = {"formerly known as", "formerly also known as", "also known as", "formerly",
			"previously known as","previously","please refer to"};

	@Override
	public List<String> getAliases(String companyName) {
		List<String> aliases = new ArrayList<String>();
		addAliases(companyName, aliases);
		return aliases;
	}

	private void addAliases(String companyName, List<String> aliases) {
		// Add the name as such from the source
		aliases.add(companyName);
		String[] names = companyName.split("\\(");
		addCurrentNameAliases(names[0], aliases);
		if (names.length > 1)
		{
			addFormerNameAliases(names[1], aliases);
		}
	}

	private void addCurrentNameAliases(String currentName, List<String> aliases) {
		aliases.add(currentName);
		addNameWithoutLegalExtensions(currentName, aliases);
	}

	private void addFormerNameAliases(String formerName, List<String> aliases) {
		
		formerName = removeTextsFromFormerName(formerName);
		if (formerName != null)
		{
			aliases.add(formerName);
			addNameWithoutLegalExtensions(formerName, aliases);
		}
	}

	private void addNameWithoutLegalExtensions(String name, List<String> aliases) {
		for (String string : COMPANY_EXTENSIONS)
		{
			if (StringUtils.endsWithIgnoreCase(name, string))
			{
				aliases.add(StringUtils.remove(name, string));
				break;
			}
		}
	}
	
	private String removeTextsFromFormerName(String formerName)
	{
		formerName = formerName.replace(SpecialCharacters.OPENING_PARANTHESES.toString(), "").replace(SpecialCharacters.CLOSING_PARANTHESES.toString(), "");
		boolean textFound = false;
		for (String string : FORMER_NAME_TEXTS)
		{
			if (StringUtils.startsWith(formerName, string))
			{
				formerName = StringUtils.remove(formerName, string);
				textFound = true;
				break;
			}
		}
		if (!textFound) {
			formerName = null;
		}
		return formerName;
	}

}
